package Thogakade.control;

import Thogakade.HibernateUtil;
import Thogakade.entity.Customer;
import Thogakade.view.tm.CustomerTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
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
import java.util.List;

public class CustomerFormController {

    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colSalary;
    public TableColumn colOption;
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public Button btn;
    public TableView<CustomerTM> tbl;
    public AnchorPane CustomerFormContext;

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        loadCustomers("");

    tbl.getSelectionModel()
            .selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> {
                setData=newValue;
        });
    }





    private void loadCustomers(String searchText) {
        ObservableList<CustomerTM> tmList=FXCollections.observableArrayList();
        try (Session session= HibernateUtil.createSession()){
            Query from_customer=session.createQuery("FROM Customer");
            List<Customer> list = from_customer.list();
            for (Customer c : list
            ) {
                tmList.add(new CustomerTM(c.getId(),c.getName(),c.getAddress(),c.getSalary()));
            }
            tbl.setItems(tmList);

        }
    }
    public void SaveCustomer(ActionEvent actionEvent) {
        Customer c1 = new Customer(
                txtId.getText(), txtName.getText(), txtAddress.getText(),
                Double.parseDouble(txtSalary.getText())
        );
        try (Session session = HibernateUtil.createSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(c1);
            transaction.commit();
            new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
            loadCustomers("");
        }
    }
    public void newCustomer(ActionEvent actionEvent) {
        clearData();
        btn.setText("Save Customer");
    }
    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) CustomerFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader
                .load(getClass().getResource("../view/DashboardForm.fxml"))));
    }
    private void clearData() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
    }
    CustomerTM setData = null;
}
