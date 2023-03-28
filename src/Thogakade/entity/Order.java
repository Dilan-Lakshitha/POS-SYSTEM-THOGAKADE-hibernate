package Thogakade.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private String id;
    @Column(name = "order_date")
    private String orderDate;
    private double cost;

    @ManyToOne
    @JoinColumn(
            name = "customer_id",
            nullable = false
    )
    private Customer customer;

    @OneToMany(mappedBy = "order",cascade = {
            CascadeType.ALL
    })
    private List<OrderDetails> details= new ArrayList<>();


    public Order() {
    }

    public Order(String id, String orderDate, double cost,List<OrderDetails>details) {
        this.id = id;
        this.orderDate = orderDate;
        this.cost = cost;
        this.details=details;
    }

    public Order(String text, String value, Date date, double parseDouble) {
    }

    public Order(String text, String toString, double parseDouble) {
    }

    public List<OrderDetails> getdetails() {
        return details;
    }

    public void setdetails(List<OrderDetails> details) {
        this.details = details;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
