package com.developer.foodfit.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="item_id")
    @NotNull
    private Item item;

    @ManyToOne
    @JoinColumn(name="order_id")
    @NotNull
    private Order order;

    @NotNull
    private int orderPrice;

    @NotNull
    private int count;

    @NotNull
    private LocalDateTime regTime;

    @NotNull
    private LocalDateTime updateTime;

    @Builder
    public OrderItem(Long id, Item item, Order order, int orderPrice, int count, LocalDateTime regTime, LocalDateTime updateTime) {
        this.id = id;
        this.item = item;
        this.order = order;
        this.orderPrice = orderPrice;
        this.count = count;
        this.regTime = regTime;
        this.updateTime = updateTime;
    }
}
