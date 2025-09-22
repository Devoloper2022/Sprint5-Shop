package org.example.intershop.models.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;



@Table(name = "item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    private Long id;
    private String title;
    private String description;
    private String imgname;
    private Long price;

}
