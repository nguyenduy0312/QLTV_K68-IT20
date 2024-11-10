package view.user;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddUser extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("adduser.fxml"));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
