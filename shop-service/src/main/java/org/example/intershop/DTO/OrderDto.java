package org.example.intershop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private List<ItemDto> items;
    private Integer totalSum = 0;
    private Integer quantity = 0;

}
