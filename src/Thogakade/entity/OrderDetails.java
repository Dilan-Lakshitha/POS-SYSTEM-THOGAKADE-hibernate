package Thogakade.entity;

import javax.persistence.*;

@Entity
@Table(name="order_details")
public class OrderDetails {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    private int qty;
    @Column(name ="unit_price")
    private double unitPrice;

    @ManyToOne
    @JoinColumn(
            name = "item_code",
            updatable = false,
            insertable = false,
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_item_code"
            )
    )
    private Item item;

    @ManyToOne
    @JoinColumn(
            name = "order_code",
            updatable = false,
            insertable = false,
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_order_code"
            )
    )
    private Order order;

    public OrderDetails() {
    }

    public OrderDetails(long id, int qty, double unitPrice) {
        this.id = id;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public Item getItem() {
        return item;
    }

    public void setItem() {
        this.item = item;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public long getId() {
        return id;
    }

    public void setId() {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "id=" + id +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", item=" + item +
                ", order=" + order +
                '}';
    }
}
