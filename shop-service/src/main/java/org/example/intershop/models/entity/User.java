package org.example.intershop.models.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    private Long id;

    private String username;

    private String password;
}
