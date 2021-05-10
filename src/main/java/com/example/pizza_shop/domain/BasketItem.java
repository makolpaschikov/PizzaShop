package com.example.pizza_shop.domain;

import javax.persistence.*;

@Entity
public class BasketItem {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemID;

    private Long productID;

    private String info;

    public Long getItemID() {
        return itemID;
    }

    public void setItemID(Long itemID) {
        this.itemID = itemID;
    }
}
