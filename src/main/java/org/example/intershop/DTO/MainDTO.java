package org.example.intershop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
public class MainDTO {
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalSize;


    public MainDTO(Integer pageSize, Integer pageNumber) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public Boolean hasNext() {
        return (pageNumber - 1) * pageSize < totalSize;
    }

    public Boolean hasPrevious() {
        return (pageNumber - 1) * pageSize > totalSize;
    }


    public Pageable getPageable(SortType sort) {
        return switch (sort) {
            case NO -> PageRequest.of(pageNumber, pageSize);
            case ALPHA -> PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "title"));
            case PRICE -> PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "price"));
        };
    }
}
