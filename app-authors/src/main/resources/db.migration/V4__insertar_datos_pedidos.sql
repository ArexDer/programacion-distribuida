-- ***********************************************************************
-- Script: V4__insertar_datos_pedidos.sql
-- Descripción: Inserta registros de ejemplo en las tablas customers,
-- purchase_orders y line_items.
-- Autor: TuNombre
-- Fecha: 2025-05-20
-- ***********************************************************************

-- =========================
-- Insertar clientes
-- =========================
INSERT INTO customers (name, email, version) VALUES ('Juan Pérez', 'juanperez@gmail.com', 1);
INSERT INTO customers (name, email, version) VALUES ('Ana López', 'ana.lopez@hotmail.com', 1);
INSERT INTO customers (name, email, version) VALUES ('Carlos Ramírez', 'carlosramirez@yahoo.com', 1);

-- =========================
-- Insertar órdenes de compra
-- =========================
INSERT INTO purchase_orders (customer_id, total, status, placed_on, delivered_on)
VALUES (1, 50, 1, '2025-05-19 10:00:00', '2025-05-20 14:00:00');

INSERT INTO purchase_orders (customer_id, total, status, placed_on, delivered_on)
VALUES (2, 75, 2, '2025-05-18 12:30:00', '2025-05-21 16:00:00');

-- =========================
-- Insertar ítems de órdenes
-- =========================
INSERT INTO line_items (order_id, quantity, isbn) VALUES (1, 2, 'ISBN-001');
INSERT INTO line_items (order_id, quantity, isbn) VALUES (1, 1, 'ISBN-003');
INSERT INTO line_items (order_id, quantity, isbn) VALUES (2, 3, 'ISBN-005');
