package Thogakade.entity;

import Thogakade.view.tm.OrderDetailsTM;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Item {
    @Id
    private String code;
    private String description;
    @Column(name = "unit_price")
    private double unitPrice;
    @Column(name = "total_cost")
    private int qtyOnHand;

    @OneToMany(mappedBy = "item",cascade = {
            CascadeType.ALL
    })
    private List<OrderDetails> details= new ArrayList<>();

    public Item() {
    }

    public Item(String code,String description, double unitPrice, int qtyOnHand) {
        this.code = code;
        this.description=description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public List<OrderDetails> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetails> details) {
        this.details = details;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getTotalCost() {
        return qtyOnHand;
    }

    public void setTotalCost(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "Item{" +
                "code='" + code + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                ", details=" + details +
                '}';
    }
}

