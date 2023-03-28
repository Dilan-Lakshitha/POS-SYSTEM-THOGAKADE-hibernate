package Thogakade.view.tm;

public class OrderTM{
    private String orderId;
    private String date;
    private double cost;
    private String customerId;
    private String customerName;

    public OrderTM() {
    }

    public OrderTM(String orderId, String date, double cost, String customerId, String customerName) {
        this.orderId = orderId;
        this.date = date;
        this.cost = cost;
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}

