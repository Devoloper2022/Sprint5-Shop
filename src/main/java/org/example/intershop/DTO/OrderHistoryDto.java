package org.example.intershop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryDto {
    private Long orderId;
    private Integer count;
    private Integer price;
    private Integer quantity;
}
