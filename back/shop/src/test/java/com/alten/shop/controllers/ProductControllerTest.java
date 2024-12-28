package com.alten.shop.controllers;

import com.alten.shop.dtos.requests.ProductDTO;
import com.alten.shop.dtos.requests.UserDTO;
import com.alten.shop.services.impl.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {

    private final ProductService productService;
    ObjectMapper objectMapper;

    private final TestRestTemplate restTemplate;
    private String jwt;

    @BeforeEach
    private void getJWT() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
         HttpEntity<String> request=new HttpEntity<>(objectMapper.writeValueAsString(buildUserDTO()),headers);
        ResponseEntity<String> syntheseResponse =restTemplate.postForEntity("/api/v1/accounts/token", request, String.class);
        this.jwt=syntheseResponse.getBody();
    }
    @Autowired
    public ProductControllerTest(ProductService productService,TestRestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper=objectMapper;
        this.productService=productService;
    }
    @Test
    @Order(1)
    void addNewProduct() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION,"Bearer ".concat(jwt));
        HttpEntity<String> request=new HttpEntity<>(objectMapper.writeValueAsString(buildProductDTO()),headers);
        ResponseEntity<ProductDTO> syntheseResponse =restTemplate.postForEntity("/api/v1/products", request, ProductDTO.class);
        Assertions.assertEquals(HttpStatusCode.valueOf(200),syntheseResponse.getStatusCode());
        Assertions.assertNotNull(syntheseResponse.getBody());
    }

    @Test
    @Order(2)
    void getAllProduct(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<HttpHeaders> request=new HttpEntity<>(headers);
        ResponseEntity<List<ProductDTO>> listResponseEntity=restTemplate.exchange("/api/v1/products",HttpMethod.GET,request,new ParameterizedTypeReference<List<ProductDTO>>() {});
        Assertions.assertEquals(HttpStatusCode.valueOf(200),listResponseEntity.getStatusCode());
        Assertions.assertEquals(2,listResponseEntity.getBody().size());
    }
    @Test
    @Order(3)
    void getProductByIdTest(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<HttpHeaders> request=new HttpEntity<>(headers);
        ResponseEntity<ProductDTO> response=restTemplate.exchange("/api/v1/products/2",HttpMethod.GET,request,ProductDTO.class);
        Assertions.assertEquals(HttpStatusCode.valueOf(200),response.getStatusCode());
        Assertions.assertEquals(buildProductDTO().getCode(),response.getBody().getCode());
        Assertions.assertEquals(buildProductDTO().getCategory(),response.getBody().getCategory());
        Assertions.assertEquals(buildProductDTO().getDescription(),response.getBody().getDescription());

    }

    @Test
    @Order(4)
    void updateProduct(){
        ProductDTO productDTO=buildProductDTO();
        productDTO.setCode("new Value");
        productDTO.setId(1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION,"Bearer ".concat(jwt));
        HttpEntity<ProductDTO> request=new HttpEntity<>(productDTO,headers);
        ResponseEntity<ProductDTO> response=restTemplate.exchange("/api/v1/products/2",HttpMethod.PATCH,request,ProductDTO.class);
        Assertions.assertEquals(HttpStatusCode.valueOf(200),response.getStatusCode());
        Assertions.assertEquals(productDTO.getCode(),response.getBody().getCode());
        Assertions.assertEquals(productDTO.getCategory(),response.getBody().getCategory());
        Assertions.assertEquals(productDTO.getDescription(),response.getBody().getDescription());

    }
    @Test
    @Order(5)
    void  deleteProductById(){
        ProductDTO productDTO=buildProductDTO();
        productDTO.setCode("new Value");
        productDTO.setId(1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION,"Bearer ".concat(jwt));
        HttpEntity<ProductDTO> request=new HttpEntity<>(headers);
        Assertions.assertEquals(2,productService.getAllProducts().size());
        ResponseEntity<String> response=restTemplate.exchange("/api/v1/products/2",HttpMethod.DELETE,request,String.class);
        Assertions.assertEquals(HttpStatusCode.valueOf(200),response.getStatusCode());
        Assertions.assertEquals(1,productService.getAllProducts().size());
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
    private UserDTO buildUserDTO(){
        UserDTO userDTO=new UserDTO();
        userDTO.setPassword("123654");
        userDTO.setEmail("admin@admin.com");
        return userDTO;
    }
}
