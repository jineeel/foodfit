package com.developer.foodfit.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class CartItem {

    @Id
    @GeneratedValue
    @Column(name="cart_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name="item_id")
    private Item item;

    private int count;

    @Builder
    public CartItem(Cart cart, Item item, int count) {
        this.cart = cart;
        this.item = item;
        this.count = count;
    }

    public void updateCount(int count){
        this.count = (this.count+count);
    }
    public void update(int count){
        this.count = count;
    }
}
