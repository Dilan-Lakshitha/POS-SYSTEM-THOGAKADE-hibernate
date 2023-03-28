package Thogakade;

import Thogakade.entity.Item;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/DashboardForm.fxml"))));
        primaryStage.centerOnScreen();
        primaryStage.show();

    }
    private void setItemDetailsIfEmpty() {
        try(Session session= HibernateUtil.createSession()){
            Query from_item = session.createQuery("FROM Item");
            List list = from_item.list();
            if (list.isEmpty()){
                // setData

                Item item1 = new Item("D-001","Description 1",25,2500);
                Item item2 = new Item("D-002","Description 2",34,4355);
                Item item3 = new Item("D-003","Description 3",20,2234);
                Item item4 = new Item("D-004","Description 4",30,5854);
                saveData(item1);
                saveData(item2);
                saveData(item3);
                saveData(item4);
                new Alert(Alert.AlertType.CONFIRMATION, "Items Saved..").show();

            }
        }

    }
    private static void saveData(Item i) {
        try (Session session = HibernateUtil.createSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(i);
            transaction.commit();
        }
    }
}
