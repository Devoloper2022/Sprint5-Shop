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
    @Column(nullable = false)
    private int totalPrice;
    @Column(nullable = false)
    private int totalQuantity;
    @Column(nullable = false)
    private int totalPosition;
    @Column(nullable = false)
    private boolean status;
}
