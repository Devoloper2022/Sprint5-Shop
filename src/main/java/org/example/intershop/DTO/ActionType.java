package org.example.intershop.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActionType {
    PLUS("plus", "Добавить один товар"),
    MINUS("minus", "Удалить один товар");

    private final String name;
    private final String description;
}
