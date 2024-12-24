package com.alten.shop.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class ProductDTO implements Serializable {
    private int id;
    @NotEmpty(message = "Product code should not be empty")
    @NotNull(message = "Product code should not be null")
    private String code;
    @NotEmpty(message = "Product name should not be empty")
    @NotNull(message = "Product name should not be null")
    private String name;
    private String description;
    private String  image;
    @NotEmpty(message = "Product category should not be empty")
    @NotNull(message = "Product category should not be null")
    private String  category;
    @NotNull(message = "Product price should not be null")
    @DecimalMin(value = "0.01", inclusive = true, message = "Product price must be greater than zero")
    private float price;
    @NotNull(message = "Product quantity should not be null")
    @Min(value = 0, message = "Product quantity must be equal or greater than zero")
    private int quantity;
    @NotEmpty(message = "Internal reference should not be empty")
    @NotNull(message = "Internal reference should not be null")
    private String  internalReference;
    @NotNull(message = "Shell id should not be null")
    private int shellId;
    @NotNull(message = "Inventory status should not be null")
    private int inventoryStatusCode;
    private String inventoryStatusLabel;
    @NotNull(message = "Rating should not be null")
    @Min(value = 1, message = "Product rating must range from 1 to 5")
    @Max(value = 5, message = "Product rating must range from 1 to 5")
    private float rating;
    private Date createdAt;
    private Date updatedAt;
}