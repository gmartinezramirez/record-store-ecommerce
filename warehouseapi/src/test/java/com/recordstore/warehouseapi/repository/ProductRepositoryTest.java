package com.recordstore.warehouseapi.repository;

import com.recordstore.warehouseapi.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product(1, "Item", 2500.0f);
    }

    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();
        product = null;
    }

    @Test
    void givenOneProductAdded_whenFindById_thenReturnAddedProduct() {

        // Given
        productRepository.save(product);
        // When
        Product fetchedProduct = productRepository.findById(product.getId()).get();
        // Then
        var expectedProductId = 1;
        assertEquals(expectedProductId, fetchedProduct.getId());
    }

    @Test
    void givenTwoProductsAdded_whenFindAll_thenReturnProductsSaved() {

        // Given
        Product product1 = new Product(1, "firstProduct", 400.0f);
        Product product2 = new Product(2, "secondProduct", 500.0f);
        productRepository.save(product1);
        productRepository.save(product2);

        // When
        List<Product> productList = productRepository.findAll();
        // Then
        var expectedProductName = "secondProduct";
        assertEquals(expectedProductName, productList.get(1).getName());

    }

    @Test
    void givenProductSaved_whenFindById_thenReturnProductOfThatId() {

        // Given
        Product product1 = new Product(1, "aProduct", 3000.0f);
        Product product2 = productRepository.save(product1);

        // When
        Optional<Product> optional = productRepository.findById(product2.getId());
        // Then
        assertEquals(product2.getId(), optional.get().getId());
        assertEquals(product2.getName(), optional.get().getName());
    }

    @Test
    void givenProductSaved_whenDeleteByHisId_thenShouldDeleteTheProduct() {

        // Given
        Product product = new Product(4, "aProduct", 160.0f);
        productRepository.save(product);

        // When
        productRepository.deleteById(product.getId());
        // Then
        Optional optional = productRepository.findById(product.getId());
        assertEquals(Optional.empty(), optional);
    }

}
