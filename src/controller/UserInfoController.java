package controller;

import DAO.DocumentDAO;
import DAO.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Document;
import model.User;
import util.Date;

import java.io.ByteArrayInputStream;

public class UserInfoController {
    @FXML
    private Button closeButton;

    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label gender;
    @FXML
    private Label dateOfBirth;
    @FXML
    private Label phoneNumber;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label userName;
    @FXML
    private Label password;
    @FXML
    private ImageView imageView;
    public void closeButtonOnAction(ActionEvent e) {
        closeButton.getScene().getWindow().hide();
    }

    public void setInfoById(String id) {

        UserDAO userDAO = new UserDAO();
        User user = userDAO.findUserById(id);

        this.id.setText(user.getId());
        this.name.setText(user.getName());
        this.gender.setText(user.getGender());
        this.dateOfBirth.setText(user.getBirthday().toString());
        this.phoneNumber.setText(user.getPhoneNumber());
        this.address.setText(user.getAddress());
        this.email.setText(user.getEmail());
        this.userName.setText(user.getAccount().getUserName());
        this.password.setText(user.getAccount().getPassWord());

        if(user.getPicture() != null) {
            // Chuyển byte array thành Image
            ByteArrayInputStream bis = new ByteArrayInputStream(user.getPicture());
            Image image = new Image(bis);
            this.imageView.setImage(image);
            this.imageView.setPreserveRatio(true);
        }
    }

}
