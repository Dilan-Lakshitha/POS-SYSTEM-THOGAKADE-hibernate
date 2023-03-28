package Thogakade.control;

import Thogakade.HibernateUtil;
import Thogakade.entity.Customer;
import Thogakade.entity.Item;
import Thogakade.entity.Order;
import Thogakade.entity.OrderDetails;
import Thogakade.view.tm.CartTM;
import Thogakade.view.tm.CustomerTM;
import Thogakade.view.tm.OrderTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class PlaceOrderFormController {
    public TextField txtCustomerName;
    public ComboBox<String>cmbItemCode;
    public ComboBox<CartTM> cmbCustomerId;

    public TextField txtAddress;
    public TextField txtSalary;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public TextField txtRequestQty;
    public TableView<OrderTM> tblCart;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colTotal;
    public TableColumn colOption;
    public TableView<CartTM> tblOrders;
    public Label lblTotal;
    public AnchorPane context;
    public Label lblOrderId;

    public void initialize() {
        //======
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        cmbCustomerId.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
            setCustomerData=newValue;

        });

        cmbItemCode.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setItemData= String.valueOf(newValue);
            }
        });
    }
    private void loadCustomers() {
        ObservableList<CartTM> tmList = FXCollections.observableArrayList();
        try (Session session = HibernateUtil.createSession()) {
            Query from_customer =
                    session.createQuery("FROM Customer");
            List<Customer> list = from_customer.list();
            for (Customer c : list
            ) {
                tmList.add(new CartTM(c.getId(), c.getName(), c.getAddress(), c.getSalary()));
            }
            cmbCustomerId.setItems(tmList);
        }
    }
    private void loadAllItemCodes(Item I) {
        try (Session session = HibernateUtil.createSession()) {
            Query from_item =
                    session.createQuery("FROM Item");
            List<Item> list = from_item.list();
            for (Item i : list
            ) {
                cmbItemCode.getItems().add(i.getCode());
            }
        }
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader
                .load(getClass().getResource("../view/DashboardForm.fxml"))));
    }

    ObservableList<CartTM> tmList = FXCollections.observableArrayList();
    public void addToCartOnAction(ActionEvent actionEvent) {
        if (cmbItemCode.getValue()==null){
            new Alert(Alert.AlertType.WARNING,"SELECT an Item").show();
            return;
        }
        tmList.add(new CartTM(cmbItemCode.getValue().toString(),Integer.parseInt(txtQtyOnHand.getText())));
        tblOrders.setItems(tmList);
    }
    private void calculateTotal() {
        double total = 0;
        for (CartTM tm : tmList) {
            total += tm.getTotal();
        }
        lblTotal.setText(String.valueOf(total));
    }

    private void clear() {
        cmbItemCode.setValue(null);
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtRequestQty.clear();
    }

    public void saveOrder(ActionEvent actionEvent) {
        if (setCustomerData == null) {
            new Alert(Alert.AlertType.WARNING, "Select a Customer").show();
            return;
        }
        Order o=new Order(lblOrderId.getText(), String.valueOf(cmbCustomerId.getValue()),new Date(),Double.parseDouble(lblTotal.getText()));

        try (Session session = HibernateUtil.createSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(o);
            transaction.commit();
            for (CartTM tm: tmList
            ) {
                saveItemDetails(o, tm);
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
            loadOrders();
        }

    }
    private void saveItemDetails(Order o, CartTM tm) {
        try(Session session= HibernateUtil.createSession()){
            Item i =session.get(Item.class,tm.getCode());
            System.out.println(i);
            OrderDetails od= new OrderDetails(0,tm.getQty(),i.getUnitPrice());
            od.setItem();
            od.setOrder(o);
            System.out.println(od);

            Transaction transaction = session.beginTransaction();
            session.save(od);
            transaction.commit();
        }
    }

    private void loadOrders() {
        ObservableList<OrderTM> tmList = FXCollections.observableArrayList();
        try (Session session = HibernateUtil.createSession()) {
            Query from_orders =
                    session.createQuery("FROM Order");
            List<Order> list = from_orders.list();

            for (Order o : list
            ) {
                tmList.add(new OrderTM(
                        o.getId(),
                        o.getOrderDate(), o.getCost(),
                        o.getCustomer().getId(), o.getCustomer().getName()));
            }
            tblCart.setItems(tmList);

        }
    }
    CartTM setCustomerData = null;

    String setItemData=null;

}
