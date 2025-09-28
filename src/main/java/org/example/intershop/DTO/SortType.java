package org.example.intershop.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
@AllArgsConstructor
public enum SortType {
    NO("Без сортировки"),
    ALPHA("По алфавиту"),
    PRICE("По цене");

    private final String description;

    public Sort getSort(SortType sort) {
        return switch (sort) {
            case NO -> Sort.by("id").ascending();
            case ALPHA -> Sort.by("title").ascending();
            case PRICE -> Sort.by("price").ascending();
        };
    }


    public String fromValue(SortType sort) {
        return switch (sort) {
            case NO -> "id";
            case ALPHA -> "title";
            case PRICE -> "price";
        };
    }
}
