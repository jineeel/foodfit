package com.developer.foodfit.domain;

import com.developer.foodfit.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    private String orderName;
    private String orderPhone;
    private String orderZipcode;
    private String orderStreetAdr;
    private String orderDetailAdr;
    private String orderMessage;

    private String orderViewStatus;
    @Builder
    public Order(Long id, User user, List<OrderItem> orderItems, LocalDateTime orderDate, OrderStatus orderStatus, LocalDateTime regTime, LocalDateTime updateTime,
                 String orderName, String orderPhone, String orderZipcode, String orderStreetAdr, String orderDetailAdr, String orderMessage, String orderViewStatus) {
        this.id = id;
        this.user = user;
        this.orderItems = orderItems;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.regTime = regTime;
        this.updateTime = updateTime;
        this.orderName = orderName;
        this.orderPhone = orderPhone;
        this.orderZipcode = orderZipcode;
        this.orderStreetAdr = orderStreetAdr;
        this.orderDetailAdr = orderDetailAdr;
        this.orderMessage =orderMessage;
        this.orderViewStatus = orderViewStatus;
    }
    public void updateOrderViewStatus(String orderViewStatus){
        System.out.println("orderViewStatus=="+orderViewStatus);
        this.orderViewStatus = orderViewStatus;
        System.out.println("orderViewStatu222222s=="+this.orderViewStatus);
    }



}
