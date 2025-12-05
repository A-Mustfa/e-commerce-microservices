package org.taskmanagement.orderservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private Integer quantity = 0;

    @Column(name= "total_price")
    private BigDecimal totalPrice;

    @Column(name = "unit_price",nullable = false)
    private BigDecimal unitPrice;

    public static CartItem buildCartItem(Cart cart,Item item,Integer quantity){
        BigDecimal unitPrice = item.getPrice();
        BigDecimal totalPrice = item.getPrice().multiply(BigDecimal.valueOf(quantity));

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setQuantity(quantity);
        cartItem.setUnitPrice(item.getPrice());
        cartItem.setTotalPrice(totalPrice);
        return cartItem;
    }

    @PrePersist // to run this method before persisting to database for first time only
    @PreUpdate  // to run this method each time we are updating the cart
    public void calculatePrices() {
        if (item != null && quantity != null) {
            this.unitPrice = item.getPrice();
            this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        calculatePrices(); // Recalculate when quantity changes
    }
}
