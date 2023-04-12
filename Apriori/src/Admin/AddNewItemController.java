package Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AddNewItemController {

    @FXML
    private Button addNewBtn;

    @FXML
    private Button back;

    @FXML
    private TextField desField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    void addNewItem(ActionEvent event) throws SQLException {
        String name=nameField.getText();
        String description=desField.getText();
        int price=Integer.parseInt(priceField.getText());
        (new ItemInsertion()).insert(name,description,price);
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        final Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image("logo.png"));
        primaryStage.setTitle("eShop");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
