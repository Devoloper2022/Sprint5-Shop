package org.example.intershop.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@AllArgsConstructor
public enum SortType {
    NO("Без сортировки"),
    ALPHA("По алфавиту"),
    PRICE("По цене");

    private final String description;

    public String fromValue(SortType sort) {
        return switch (sort) {
            case NO -> "id";
            case ALPHA -> "title";
            case PRICE -> "price";
        };
    }
}
