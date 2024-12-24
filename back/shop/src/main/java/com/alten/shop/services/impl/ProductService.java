package com.alten.shop.services.impl;

import com.alten.shop.dtos.requests.ProductDTO;
import com.alten.shop.entities.Product;
import com.alten.shop.enums.InventoryStatusEnum;
import com.alten.shop.repositories.ProductRepository;
import com.alten.shop.services.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {
    ObjectMapper objectMapper;
    ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository,ObjectMapper objectMapper){
        this.productRepository=productRepository;
        this.objectMapper=objectMapper;
    }

    @Override
    public ProductDTO addNewProduct(ProductDTO productDTO) {
        Product product=objectMapper.convertValue(productDTO,Product.class);
        product.setInventoryStatus(productDTO.getInventoryStatusCode());
        product=productRepository.save(product);
        productDTO=objectMapper.convertValue(product,ProductDTO.class);
        productDTO.setInventoryStatusCode(product.getInventoryStatus());
        productDTO.setInventoryStatusLabel(InventoryStatusEnum.findLabelByCode(product.getInventoryStatus()));
        return productDTO;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products=productRepository.findAll();
        List<ProductDTO> productDTOS=new ArrayList<>();
        products.forEach(p->productDTOS.add(objectMapper.convertValue(p,ProductDTO.class)));
        return productDTOS;
    }

    @Override
    public ProductDTO getProductById(int productID) {
        boolean isProductExist=productRepository.existsById(productID);
        if(Boolean.FALSE.equals(isProductExist)){
            return null;
        }
        Product product=productRepository.getReferenceById(productID);
        ProductDTO productDTO=objectMapper.convertValue(product,ProductDTO.class);
        productDTO.setInventoryStatusCode(product.getInventoryStatus());
        productDTO.setInventoryStatusLabel(InventoryStatusEnum.findLabelByCode(product.getInventoryStatus()));
        return productDTO;
    }

    @Override
    public ProductDTO updateProduct(int id ,ProductDTO productDTO) {
        boolean isProductExist=productRepository.existsById(id);
        if(Boolean.FALSE.equals(isProductExist)){
            return null;
        }
        Product product=productRepository.getReferenceById(id);
        Product productNew=objectMapper.convertValue(productDTO,Product.class);
        productNew.setId(product.getId());
        productNew.setInventoryStatus(productDTO.getInventoryStatusCode());
        productNew=productRepository.save(productNew);
        ProductDTO result= objectMapper.convertValue(productNew,ProductDTO.class);
        result.setInventoryStatusCode(productNew.getInventoryStatus());
        result.setInventoryStatusLabel(InventoryStatusEnum.findLabelByCode(productNew.getInventoryStatus()));
        return result;
    }

    @Override
    public int deleteProductById(int id) {
        boolean isProductExist=productRepository.existsById(id);
        if(Boolean.FALSE.equals(isProductExist)){
            return 0;
        }
        productRepository.deleteById(id);
        return 1;
    }
}
