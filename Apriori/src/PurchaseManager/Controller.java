package PurchaseManager;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    public Recommendation cart =new Recommendation();

    @FXML
    private TableView<Product> cartTable;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, Integer> Quantity;

    @FXML
    private TableColumn<Product, Double> Price;

    @FXML
    private Label totalPriceBox;


    @FXML
    void checkoutFunction(ActionEvent event) throws IOException {
        //popup

        Stage primaryStage =new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Popup.fxml"));
        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image("logo.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
        try {
            Recommendation.savefile();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        primaryStage.setOnCloseRequest( windowEvent -> {
            try {
                primaryStage.close();
                goBack(event);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    @FXML
    void goBack(ActionEvent event) throws IOException {

        cart.clearOrder();

        final Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("recommendation.fxml"));
        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image("logo.png"));
        primaryStage.setTitle("eShop");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        Quantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
        Price.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getQuantity() * cellData.getValue().getPrice()));

        double totalPrice = 0;
        for (Product product : cart.getOrder()) {
            totalPrice += product.getQuantity() * product.getPrice();
        }

        ObservableList<Product> productList = FXCollections.observableArrayList(cart.getOrder());
        cartTable.setItems(productList);
        totalPriceBox.setText(String.valueOf(totalPrice)+" Taka");

    }



}

