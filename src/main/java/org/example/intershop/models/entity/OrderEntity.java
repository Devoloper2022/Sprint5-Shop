package org.example.intershop.models.entity;


import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data
@Table(name = "orders")
@EqualsAndHashCode(of = "id")
public class OrderEntity {
    @Id
    private Long id;
    private int totalPrice;
    private int totalQuantity;
    private int totalPosition;
    private boolean status;
}

