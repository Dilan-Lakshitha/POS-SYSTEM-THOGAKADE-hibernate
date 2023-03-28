package Thogakade.control;

import Thogakade.HibernateUtil;
import Thogakade.entity.Order;
import Thogakade.view.tm.OrderTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class OrderFormController {
    public TableView<OrderTM> tblOders;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colCost;
    public TableColumn colDate;
    public AnchorPane ContextForm;

    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        tblOders.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
             if(newValue!=null){
                 try {
                     loadDetails(newValue.getOrderId());
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }
             }
        });
    }
    private void loadDetails(String orderId) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/OrderDetailsForm.fxml"));
        Parent load = loader.load();
        OrderDetailsFormController controller = loader.getController();
        controller.setOrder(orderId,load);
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.show();
        stage.centerOnScreen();
    }

    private void loadData() {
        ObservableList<OrderTM> tmList = FXCollections.observableArrayList();
        try (Session session= HibernateUtil.createSession()) {
            Query from_orders=session.createQuery("FROM Order");
            List<Order> list=from_orders.list();
            for (Order o:list
                 ) {tmList.add(new OrderTM(
                         o.getId(),
                         o.getOrderDate(),
                        o.getCost(),
                        o.getCustomer().getId(),o.getCustomer().getName()
            ));

            }
        }
        tblOders.setItems(tmList);
    }
    public void backToHome(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) ContextForm.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader
                .load(getClass().getResource("../view/DashboardForm.fxml"))));
    }
    OrderTM selectedCustomer = null;
}
