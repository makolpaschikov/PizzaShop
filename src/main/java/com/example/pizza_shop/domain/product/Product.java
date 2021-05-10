package com.example.pizza_shop.domain.product;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productID;
    private String name;
    private String cost;
    private String composition;

    @CollectionTable(name = "product_type", joinColumns = @JoinColumn(name = "product_id"))
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    public Product() {
    }

    public Product(String name, String cost, String composition, ProductType productType) {
        this.name = name;
        this.cost = cost;
        this.composition = composition;
        this.productType = productType;
    }

    public Product(Long id, String name, String cost, String composition, ProductType productType) {
        this.productID = id;
        this.name = name;
        this.cost = cost;
        this.composition = composition;
        this.productType = productType;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        switch (productType) {
            case PIZZA: builder.append("Пицца: "); break;
            case DESSERT: builder.append("Десерт: "); break;
            case DRINK: builder.append("Напиток: "); break;
            case SNACK: builder.append("Закуска: "); break;
        }
        builder.append(name).append(".\n");
        return builder.toString();
    }
}
