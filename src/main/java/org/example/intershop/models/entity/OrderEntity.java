package org.example.intershop.models.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true)
    private int totalPrice;
    @Column(nullable = true)
    private int totalQuantity;
    @Column(nullable = true)
    private int totalPosition;
    @Column(nullable = true)
    private boolean status;
}
