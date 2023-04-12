package Admin;

import PurchaseManager.Apriori;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AdminHome extends Application {
//    public static void main(String[] args) throws SQLException, IOException {
//        Scanner inp1=new Scanner(System.in);
//
//        String name=inp1.nextLine();
//        String details=inp1.nextLine();
//        int price=inp1.nextInt();
//
//        ItemInsertion.insert(name,details,price);
//        ItemManagement.showAllItems();
//        (new Apriori()).updateApriori();
//    }
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("eShop");
            primaryStage.getIcons().add(new Image("logo.png"));
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
