package PurchaseManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class Recommendation implements Initializable {


    @FXML
    private TableColumn<Product, Double> price;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, Integer> quantity;

    @FXML
    private TableColumn<Product, Void> buttonColumn;

    @FXML
    private TableView<Product> table;

    @FXML
    private ListView<String> recList;


    ObservableList<String> list= FXCollections.observableArrayList();

    @FXML
    void placeOrder(ActionEvent event) throws IOException {


        final Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Checkout.fxml"));
        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image("logo.png"));
        primaryStage.setTitle("eShop");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static ObservableList<Product> order= FXCollections.observableArrayList();

    public ObservableList<Product> getOrder() {
        return order;
    }

    public void clearOrder(){
        order.clear();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        quantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        Apriori apriori=new Apriori();

        // buttons
        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> addToCartCellFactory = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {

            public TableCell<Product, Void> call(TableColumn<Product, Void> param) {
                TableCell<Product, Void> cell = new TableCell<Product, Void>() {
                    final Button addButton = new Button("+");
                    final Button removeButton = new Button("-");
                    final HBox buttonsBox = new HBox(addButton, removeButton);

                    {
                        buttonsBox.setAlignment(Pos.CENTER);
                        buttonsBox.setSpacing(5);
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            // Get the product object from the current row
                            Product product = getTableView().getItems().get(getIndex());

                            // Set the action for the "Add" button
                            addButton.setOnAction(event -> {
                                product.setQuantity(product.getQuantity() + 1);
                                table.refresh();

                                // Add the product to the order list
                                if (!order.contains(product)) {
                                    order.add(product);

                                }
                                ArrayList<String> cart= new ArrayList<String>();
                                for(Product item1:order){
                                    cart.add(item1.getName());
                                }

                                ArrayList<String> rec=apriori.generateRecommendations(cart);
//                                System.out.println("a"+rec.size());
                                list=FXCollections.observableArrayList(rec);
                                recList.setItems(list);



                            });

                            // Set the action for the "Remove" button
                            removeButton.setOnAction(event -> {
                                int newQuantity = product.getQuantity() - 1;
                                if (newQuantity >= 0) {
                                    product.setQuantity(newQuantity);
                                    table.refresh();

                                    // Remove the product from the order list
                                    if (newQuantity == 0) {
                                        order.remove(product);

                                    }
                                }
                                ArrayList<String> cart= new ArrayList<String>();
                                for(Product item1:order){
                                    cart.add(item1.getName());
                                }

                                ArrayList<String> rec=apriori.generateRecommendations(cart);
//                                System.out.println(rec);
                                list=FXCollections.observableArrayList(rec);
                                recList.setItems(list);
                            });

                            // Set the buttons box as the graphic for the cell
                            setGraphic(buttonsBox);
                        }
                    }
                };
                return cell;
            }
        };
        buttonColumn.setCellFactory(addToCartCellFactory);

        ListItemClass listClass = new ListItemClass();

        table.setItems(listClass.list);

    }
    public static void savefile() throws IOException {
        Comparator<Product> byName = Comparator.comparing(Product::getName);
        order.sort(byName);
        BufferedWriter writer = new BufferedWriter(new FileWriter("Book1.csv",true));
        for (int i = 0; i < order.size(); i++) {
            Product product = order.get(i);
            writer.write(product.getName());
            if (i < order.size() - 1) {
                writer.write(",");
            }
        }
        writer.append("\n");

        writer.close();
    }

}
