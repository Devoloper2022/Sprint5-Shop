package org.example.intershop.controller;

import org.example.intershop.DTO.ItemDto;
import org.example.intershop.repository.ItemRepo;
import org.example.intershop.repository.OrderRepo;
import org.example.intershop.repository.PositionRepo;
import org.example.intershop.service.ItemService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MainController.class)
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemService itemService;

    @MockitoBean
    PositionRepo positionRepo;
    @MockitoBean
    OrderRepo orderRepo;
    @MockitoBean
    ItemRepo itemRepo;

    @Test
    void getMainPage_shouldReturnMainViewWithModelAttributes() throws Exception {

        ItemDto dto = new ItemDto();
        dto.setId(1L);
        dto.setTitle("Pizza");
        dto.setPrice(100L);

        Page<ItemDto> page = new PageImpl<>(List.of(dto));

        Mockito.when(itemService.findAllItemsPagingAndSorting(
                any(), any(), anyInt(), anyInt()
        )).thenReturn(page);

        mockMvc.perform(get("/")
                        .param("search", "pizza")
                        .param("sort", "NO")
                        .param("pageSize", "10")
                        .param("pageNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andExpect(model().attributeExists("items"))
                .andExpect(model().attributeExists("search"))
                .andExpect(model().attributeExists("sort"))
                .andExpect(model().attributeExists("paging"))
                .andExpect(model().attribute("items", List.of(dto)));
    }
}
