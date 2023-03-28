package Thogakade.control;

import Thogakade.HibernateUtil;
import Thogakade.entity.Item;
import Thogakade.entity.Order;
import Thogakade.entity.OrderDetails;
import Thogakade.view.tm.CartTM;
import Thogakade.view.tm.OrderDetailsTM;
import Thogakade.view.tm.OrderTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class OrderDetailsFormController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtDate;
    public TableView<OrderDetailsTM> tblOrderDetails;
    public TableColumn<OrderDetailsTM, String> colItem;
    public TableColumn<OrderDetailsTM, String> colDescription;
    public TableColumn<OrderDetailsTM, Integer> colQty;
    public TableColumn<OrderDetailsTM, Double> colUnitPrice;
    public TextField txtCost;
    private OrderDetailsTM tm;

    public void initialize() {
        colItem.setCellValueFactory(new PropertyValueFactory<>("code"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
    }

    public void setOrder(String orderId, Parent load) {
        ObservableList<OrderTM> tmList = FXCollections.observableArrayList();
        try (Session session = HibernateUtil.createSession()) {
            Order OI = session.get(Order.class, tmList.getClass());
            OrderDetails ob = new OrderDetails(0, tm.getQty(), tm.getUnitPrice());
            ob.setOrder(OI);
            ob.setItem();
            Transaction transaction = session.beginTransaction();
            session.save(ob);
            transaction.commit();
        }
    }

}
