package Thogakade.control;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DashboardFormController {
    public Label txtDateAndTime;
    public AnchorPane dashboardContext;

    public void initialize(){
        manageDateAndTime();
    }

    private void manageDateAndTime() {
        Timeline timeAndDate=new Timeline(new KeyFrame(Duration.ZERO,
                e-> txtDateAndTime.setText(LocalDateTime.now().
                        format(DateTimeFormatter.ofPattern("yyy-MM-dd:mm:ss")))),
                new KeyFrame(Duration.seconds(1)));
        timeAndDate.setCycleCount(Animation.INDEFINITE);
        timeAndDate.play();
    }

    public void openCustomerForm(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) dashboardContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/CustomerForm.fxml")))
        );
        stage.centerOnScreen();

    }

    public void openItemForm(ActionEvent actionEvent) {
    }

    public void openOrdersForm(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) dashboardContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/OrderForm.fxml"))));
        stage.centerOnScreen();
    }

    public void openNewOrderForm(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) dashboardContext.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/PlaceOrderForm.fxml"))));
    }

    public void openIncomeForm(ActionEvent actionEvent) {
    }
}
