package com.alten.shop.controllers;

import com.alten.shop.api.IProductAPI;
import com.alten.shop.dtos.requests.ProductDTO;
import com.alten.shop.services.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class ProductController implements IProductAPI {
    IProductService productService;
    @Autowired
    public ProductController(IProductService iProductService){
        this.productService=iProductService;
    }
    @Operation(summary = "Create New product", description = "Add New product to the data base")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produit created successfully"),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<ProductDTO> addNewProduct(ProductDTO productDTO, Authentication authentication){
        ProductDTO productDTO1=productService.addNewProduct(productDTO);
        return ResponseEntity.ok(productDTO1);
    }

    @Operation(summary = "get All Products", description = "get all products presented in the DB")
    @ApiResponse(responseCode = "200", description = "List of all products retrieved successfully")
    public ResponseEntity<List<ProductDTO>> getAllProduct(){
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @Operation(summary = "get product by ID", description = "Get Product details By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product Found"),
            @ApiResponse(responseCode = "404", description = "Product not Found")
    })
    public ResponseEntity<ProductDTO> getProductById(int id){
        ProductDTO productDTO=productService.getProductById(id);
        if(Objects.isNull(productDTO)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ProductDTO());
        }
        return ResponseEntity.ok(productDTO);
    }

    @Operation(summary = "Update the product by it s ID", description = "update product details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product Updated successfully "),
            @ApiResponse(responseCode = "404", description = "Produit not Found"),
            @ApiResponse(responseCode = "400", description = "Erreur Inputs")
    })
    public ResponseEntity<ProductDTO> updateProduct( int id,ProductDTO productDTO,Authentication authentication){
        if(id<=0){
            return ResponseEntity.badRequest().body(new ProductDTO());
        }
        ProductDTO result=productService.updateProduct(id,productDTO);
        if(Objects.isNull(result)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ProductDTO());
        }
        return ResponseEntity.ok(result);
    }
    @Operation(summary = "Delete Product By ID", description = "Delete Product By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted"),
            @ApiResponse(responseCode = "404", description = "Product not Found")
    })
    public ResponseEntity<String> deleteProductById(int id,Authentication authentication){
        int result =productService.deleteProductById(id);
        if(result==0){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
        }
        return ResponseEntity.ok().body("Product deleted succefully");

    }
}