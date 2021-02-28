package com.recordstore.warehouseapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recordstore.warehouseapi.model.Product;
import com.recordstore.warehouseapi.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;
    private Product product;

    @InjectMocks
    private ProductController productController;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() {
        product = new Product(1, "aProduct", 800);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @AfterEach
    void tearDown() {
        product = null;
    }

    @Test
    void givenAValidProductRequest_WhenControllerIsCalled_ThenReturnAValidCreatedStatus() throws Exception {

        when(productService.addProduct(any())).thenReturn(product);

        mockMvc.perform(post("/api/v1/product").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(product))).
                andExpect(status().isCreated());

        verify(productService, times(1)).addProduct(any());
    }


}
