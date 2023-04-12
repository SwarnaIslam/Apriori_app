package Admin;

import PurchaseManager.Apriori;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    @FXML
    private Button addNewBtn;

    @FXML
    private Button updateRec;

    @FXML
    void updateBtn(ActionEvent event) throws IOException {
        (new Apriori()).updateApriori();
    }
    @FXML
    void addNewBtn(ActionEvent event) throws IOException {
        final Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("AddNewItem.fxml"));
        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image("logo.png"));
        primaryStage.setTitle("eShop");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
