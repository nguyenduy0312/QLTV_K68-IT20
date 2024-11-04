package test;

import javafx.application.Application;
import javafx.stage.Stage;
import login.LoginView;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Khởi động lớp Login
        LoginView login = new LoginView();
        login.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
