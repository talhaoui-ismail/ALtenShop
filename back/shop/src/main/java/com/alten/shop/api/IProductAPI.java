package com.alten.shop.api;

import com.alten.shop.dtos.requests.ProductDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/api/v1/products")
public interface IProductAPI {
    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductDTO> addNewProduct(@Validated @RequestBody ProductDTO productDTO, Authentication authentication);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ProductDTO>> getAllProduct();
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductDTO> getProductById(@PathVariable(name = "id") int id);

    @PatchMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductDTO> updateProduct(@PathVariable(name = "id") int id,@Validated @RequestBody ProductDTO productDTO,Authentication authentication);
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> deleteProductById(@PathVariable(name = "id") int id,Authentication authentication);
}
