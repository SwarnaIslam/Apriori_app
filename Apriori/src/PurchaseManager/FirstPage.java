package PurchaseManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class FirstPage  extends Application{

        public void start(Stage primaryStage) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("recommendation.fxml"));
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

