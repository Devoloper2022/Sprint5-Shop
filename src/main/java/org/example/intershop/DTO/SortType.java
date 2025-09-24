package org.example.intershop.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
