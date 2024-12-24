package com.alten.shop.services;

import com.alten.shop.dtos.requests.ProductDTO;

import java.util.List;

public interface IProductService {
    public ProductDTO addNewProduct(ProductDTO productDTO);
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(int productID);
    ProductDTO updateProduct(int id,ProductDTO productDTO);
    int deleteProductById(int id);
}
