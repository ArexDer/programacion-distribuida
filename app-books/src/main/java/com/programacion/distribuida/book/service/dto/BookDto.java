package com.programacion.distribuida.book.service.dto;

import com.programacion.distribuida.book.db.Inventory;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString //No tiene referencias circulares por eso puedo hacerlo
public class BookDto {


    private String isbn;
    private String title;
    private BigDecimal price;
    private Integer inventorySold;
    private Integer supplier;

    private List<String> authors;
}
