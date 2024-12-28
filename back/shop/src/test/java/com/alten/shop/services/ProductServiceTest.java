package com.alten.shop.services;

import com.alten.shop.dtos.requests.ProductDTO;
import com.alten.shop.dtos.requests.UserDTO;
import com.alten.shop.entities.Product;
import com.alten.shop.enums.InventoryStatusEnum;
import com.alten.shop.repositories.ProductRepository;
import com.alten.shop.services.impl.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    ObjectMapper objectMapper;
    @Mock
    ProductRepository productRepository;
    @InjectMocks
    ProductService productService;


    @Test
    void  addNewProduct() {
         given(objectMapper.convertValue(buildProductDTO(),Product.class)).willReturn(buildProduct());
         given(productRepository.save(buildProduct())).willReturn(buildProduct());
        given(objectMapper.convertValue(buildProduct(),ProductDTO.class)).willReturn(buildProductDTO());
        ProductDTO productDTO=productService.addNewProduct(buildProductDTO());
        Assertions.assertNotNull(productDTO);

    }

    @Test
    void getAllProducts() {
        given(productRepository.findAll()).willReturn(Collections.singletonList(buildProduct()));
        given(objectMapper.convertValue(buildProduct(),ProductDTO.class)).willReturn(buildProductDTO());
        List<ProductDTO> productDTOS=productService.getAllProducts();
        Assertions.assertNotNull(productDTOS);
        Assertions.assertEquals(1,productDTOS.size());
    }

    @Test
    void  getProductByIdCas200() {
        given(productRepository.existsById(1)).willReturn(true);
        given(productRepository.getReferenceById(1)).willReturn(buildProduct());
        given(objectMapper.convertValue(buildProduct(),ProductDTO.class)).willReturn(buildProductDTO());
        ProductDTO productDTO=productService.getProductById(1);
        Assertions.assertNotNull(productDTO);
    }

    @Test
    void  getProductByIdCas404() {
        given(productRepository.existsById(1)).willReturn(false);
        ProductDTO productDTO=productService.getProductById(1);
        Assertions.assertNull(productDTO);
    }
    @Test
    void updateProduct404() {
        given(productRepository.existsById(1)).willReturn(false);
        ProductDTO productDTO=productService.updateProduct(1,buildProductDTO());
        Assertions.assertNull(productDTO);
    }
    @Test
    void updateProduct200() {
        given(productRepository.existsById(1)).willReturn(true);
        given(productRepository.getReferenceById(1)).willReturn(buildProduct());
        given(objectMapper.convertValue(buildProductDTO(),Product.class)).willReturn(buildProduct());
        given(productRepository.save(buildProduct())).willReturn(buildProduct());
        given(objectMapper.convertValue(buildProduct(),ProductDTO.class)).willReturn(buildProductDTO());
        ProductDTO productDTO=productService.updateProduct(1,buildProductDTO());
        Assertions.assertNotNull(productDTO);
    }

    @Test
    void deleteProductById404() {
        given(productRepository.existsById(1)).willReturn(false);
        int i=productService.deleteProductById(1);
        Assertions.assertEquals(0,i);
    }
    @Test
    void deleteProductById200() {
        given(productRepository.existsById(1)).willReturn(true);
        int i=productService.deleteProductById(1);
        Assertions.assertEquals(1,i);
    }

    private ProductDTO buildProductDTO(){
        ProductDTO productDTO=new ProductDTO();
        productDTO.setCode("code");
        productDTO.setName("product Name");
        productDTO.setCategory("product category");
        productDTO.setPrice(15.5f);
        productDTO.setQuantity(3);
        productDTO.setInternalReference("Internal Reference");
        productDTO.setShellId(1);
        productDTO.setInventoryStatusCode(1);
        productDTO.setRating(3f);
        return productDTO;
    }
    private Product buildProduct(){
        Product product=new Product();
        product.setCode("code");
        product.setName("product Name");
        product.setCategory("product category");
        product.setPrice(15.5f);
        product.setQuantity(3);
        product.setInternalReference("Internal Reference");
        product.setShellId(1);
        product.setInventoryStatus(1);
        product.setRating(3f);
        return product;
    }
    private UserDTO buildUserDTO(){
        UserDTO userDTO=new UserDTO();
        userDTO.setPassword("123654");
        userDTO.setEmail("admin@admin.com");
        return userDTO;
    }
}
