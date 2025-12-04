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


    public Boolean hasNext() {
        return (pageNumber - 1) * pageSize < totalSize;
    }

    public Boolean hasPrevious() {
        return (pageNumber - 1) * pageSize > totalSize;
    }


}
