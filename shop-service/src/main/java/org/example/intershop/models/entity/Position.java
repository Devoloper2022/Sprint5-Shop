package org.example.intershop.models.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "positions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Position {
    @Id
    private Long id;
    private Long orderId;
    private Long itemId;
    private Integer quantity;
    @Column("status")
    private boolean status;
}
