package com.example.pizza_shop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shop_basket")
public class Basket {
    @Id
    @Column(name = "basket_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long basketID;

    private Long usrID;

    public Basket() {
    }

    public Basket(Long usrID) {
        this.usrID = usrID;
    }

    @ElementCollection
    private List<String> products = new ArrayList<>();

    public Long getBasketID() {
        return basketID;
    }

    public void setBasketID(Long basketID) {
        this.basketID = basketID;
    }


    public Long getUsrID() {
        return usrID;
    }

    public void setUsrID(Long user) {
        this.usrID = user;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }
}
