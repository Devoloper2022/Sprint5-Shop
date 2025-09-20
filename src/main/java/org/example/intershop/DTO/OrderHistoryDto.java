package org.example.intershop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryDto {
    private int count=0;
    private int price=0;
    private List<OrderDto> list;
}
