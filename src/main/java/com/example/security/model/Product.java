package com.example.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {

    private String code;
    private String name;
    private String description;
    private double buyPrice;
    private double sellPrice;
    private int quantityInStock;

}