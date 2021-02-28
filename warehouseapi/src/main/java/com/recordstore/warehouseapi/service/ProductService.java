package com.recordstore.warehouseapi.service;

import com.recordstore.warehouseapi.model.Product;

import java.util.List;

public interface ProductService {

    Product addProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(int id);

}
