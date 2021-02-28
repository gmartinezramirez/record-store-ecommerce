package com.recordstore.warehouseapi.service;

import com.recordstore.warehouseapi.model.Product;
import com.recordstore.warehouseapi.repository.ProductRepository;
import com.recordstore.warehouseapi.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    List<Product> productList;

    private Product product1;
    private Product product2;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        productList = new ArrayList<>();
        product1 = new Product(1, "product1", 20);
        product2 = new Product(2, "product2", 200);
        productList.add(product1);
        productList.add(product2);
    }

    @AfterEach
    public void tearDown() {
        product1 = product2 = null;
        productList = null;
    }

    @Test
    void givenProductToAdd_whenAddTheProduct_thenReturnAddedProduct() {

        // Given
        when(productRepository.save(any())).thenReturn(product1);
        // When
        productService.addProduct(product1);
        // Then
        verify(productRepository, times(1)).save(any());

    }

    @Test
    void givenFindAllProducts_whenGetAllProducts_thenReturnListOfAllProducts() {

        // Given
        productRepository.save(product1);
        when(productRepository.findAll()).thenReturn(productList);

        // When
        List<Product> productList1 = productService.getAllProducts();

        // Then
        assertEquals(productList1, productList);
        verify(productRepository, times(1)).save(product1);
        verify(productRepository, times(1)).findAll();

    }

    @Test
    void givenProductId_whenFindById_thenShouldReturnProductOfThatId() {

        // Given
        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(product1));

        // When - Then
        assertThat(productService.getProductById(product1.getId())).isEqualTo(product1);
    }


}
