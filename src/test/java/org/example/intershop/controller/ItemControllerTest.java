package org.example.intershop.controller;

import org.example.intershop.DTO.ItemDto;
import org.example.intershop.service.ItemService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemService itemService; // мок вместо настоящего сервиса

    @Test
    void testGetItemById() throws Exception {
        ItemDto dto = new ItemDto(1L, "Test item","asdfnasdnf",null,0,50L,null);
        Mockito.when(itemService.getItemById(1L)).thenReturn(dto);

        mockMvc.perform(get("/items/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("item"))
                .andExpect(model().attribute("item", dto))
                .andExpect(view().name("item"));
    }
}
