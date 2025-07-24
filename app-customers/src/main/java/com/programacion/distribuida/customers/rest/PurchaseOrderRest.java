package com.programacion.distribuida.customers.rest;

import com.programacion.distribuida.customers.clients.BookRestClient;
import com.programacion.distribuida.customers.db.Customer;
import com.programacion.distribuida.customers.db.PurchaseOrder;
import com.programacion.distribuida.customers.dto.CustomerDto;
import com.programacion.distribuida.customers.dto.PurchaseOrderDto;
import com.programacion.distribuida.customers.repo.CustomerRepository;
import com.programacion.distribuida.customers.repo.PurchaseOrderRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Path("/orders")
@Transactional
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PurchaseOrderRest {

    private static final Logger log = Logger.getLogger(PurchaseOrderRest.class);

    @Inject
    ModelMapper mapper;

    @Inject
    PurchaseOrderRepository repository;

    @Inject
    CustomerRepository customerRepository;

    @Inject
    @RestClient
    BookRestClient bookRestClient;

    private PurchaseOrderDto map(PurchaseOrder source) {
        var dto = mapper.map(source, PurchaseOrderDto.class);

        dto.getLineItems().forEach(item -> {
            try {
                var book = bookRestClient.findByBook(item.getIsbn());
                item.setIsbn(book.getIsbn());
                item.setTitle(book.getTitle());
                item.setPrice(book.getPrice());
                item.setAuthors(book.getAuthors());
            } catch (Exception e) {
                log.warnf("No se pudo obtener el libro con ISBN %s", item.getIsbn());
                item.setTitle("Desconocido");
                item.setPrice(BigDecimal.ZERO);
                item.setAuthors(Collections.emptyList());
            }
        });

        BigDecimal total = dto.getLineItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        dto.setTotal(total);
        return dto;
    }

    @GET
    @Path("/customer/{customerId}")
    public List<PurchaseOrderDto> ordersByCustomer(@PathParam("customerId") Integer customerId) {
        return repository.findByCustomerId(customerId)
                .stream()
                .map(this::map)
                .toList();
    }

    @GET
    @Path("/{orderId}")
    public Response orderDetail(@PathParam("orderId") Integer orderId) {
        return repository.findByIdOptional(orderId)
                .map(this::map)
                .map(dto -> Response.ok(dto).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response insert(PurchaseOrder order) {
        order.setId(null);

        // Manejo del cliente
        if (order.getCustomer() != null && order.getCustomer().getId() == null) {
            order.getCustomer().setId(null);
            customerRepository.persist(order.getCustomer());
        } else if (order.getCustomer() != null && order.getCustomer().getId() != null) {
            Customer existingCustomer = customerRepository.findByIdOptional(order.getCustomer().getId())
                    .orElseThrow(() -> new RuntimeException("Customer no encontrado"));
            order.setCustomer(existingCustomer);
        }

        if (order.getLineItems() != null) {
            order.getLineItems().forEach(item -> {
                item.setId(null);
                item.setPurchaseOrder(order);
            });
        }

        repository.persist(order);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{orderId}")
    public Response update(@PathParam("orderId") Integer orderId, PurchaseOrder order) {
        if (repository.findByIdOptional(orderId).isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        order.setId(orderId);

        if (order.getCustomer() != null) {
            if (order.getCustomer().getId() == null) {
                order.getCustomer().setId(null);
                customerRepository.persist(order.getCustomer());
            } else {
                Customer existingCustomer = customerRepository.findByIdOptional(order.getCustomer().getId())
                        .orElseThrow(() -> new RuntimeException("Customer no encontrado"));
                order.setCustomer(existingCustomer);
            }
        }

        if (order.getLineItems() != null) {
            order.getLineItems().forEach(item -> item.setId(null));
        }

        repository.persist(order);
        return Response.ok().build();
    }

    @GET
    @Path("/customers")
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.listAll()
                .stream()
                .map(customer -> mapper.map(customer, CustomerDto.class))
                .toList();
    }

    @DELETE
    @Path("/customers/{customerId}")
    public Response deleteCustomer(@PathParam("customerId") Integer customerId) {
        if (customerRepository.findByIdOptional(customerId).isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<PurchaseOrder> customerOrders = repository.findByCustomerId(customerId);
        if (!customerOrders.isEmpty()) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        customerRepository.deleteById(customerId);
        return Response.ok().build();
    }

    @GET
    public List<PurchaseOrderDto> getAllOrders() {
        return repository.listAll()
                .stream()
                .map(this::map)
                .toList();
    }

    @DELETE
    @Path("/{orderId}")
    public Response delete(@PathParam("orderId") Integer orderId) {
        if (repository.findByIdOptional(orderId).isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        repository.deleteById(orderId);
        return Response.ok().build();
    }

    @POST
    @Path("/customers")
    public Response createCustomer(CustomerDto customerDto) {
        Customer customer = mapper.map(customerDto, Customer.class);
        customer.setId(null);
        customerRepository.persist(customer);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/customers/{customerId}")
    public Response updateCustomer(@PathParam("customerId") Integer customerId, CustomerDto customerDto) {
        if (customerRepository.findByIdOptional(customerId).isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Customer customer = mapper.map(customerDto, Customer.class);
        customer.setId(customerId);
        customerRepository.persist(customer);
        return Response.ok().build();
    }
}