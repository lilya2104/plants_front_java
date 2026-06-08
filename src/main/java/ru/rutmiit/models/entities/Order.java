package ru.rutmiit.models.entities;

import jakarta.persistence.*;
import ru.rutmiit.repositories.ProductRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    private Product product;

    @Column(columnDefinition = "INT", nullable = false)
    private int count;

    @Column(columnDefinition = "DECIMAL(10, 2)", nullable = false)
    private Double price;

    @Column(columnDefinition = "DECIMAL(10, 2)", nullable = false)
    private Double total_price;

    public Order(User user, Product product, int count) {
        this.user = user;
        this.product = product;
        this.count = count;
        this.price = product.getPrice();
        this.total_price = product.getPrice() * this.count;
    }

    public Order() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }
}

