package app.gui.controller;

import app.App;
import app.service.*;
import app.util.JavaFxUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.tinylog.Logger;

public class RegistrationController {
    RegistrationService registrationService;
    UsernameValidationService usernameValidationService;
    PasswordValidationService passwordValidationService;

    @FXML
    TextField usernameTextField;

    @FXML
    TextField passwordTextField;

    @FXML
    public void initialize() {
        registrationService = new WebRegistrationService("register");
        usernameValidationService = new StrictUsernameValidationService();
        passwordValidationService = new StrictPasswordValidationService(8);
    }

    public void onLogin(ActionEvent actionEvent) {
        JavaFxUtil.load(JavaFxUtil.getStageOfEvent(actionEvent), "FXML/login.fxml");
    }

    public void onRegister(ActionEvent actionEvent) {
        var username = usernameTextField.getText();
        var password = passwordTextField.getText();

        if (!usernameValidationService.isValidUsername(username)) {
            new Alert(Alert.AlertType.ERROR, "Invalid username. Use alphanumeric charaters only").showAndWait();
            return;
        }

        if (!passwordValidationService.isValidPassword(password)) {
            new Alert(Alert.AlertType.ERROR, "Invalid password. Use alphanumeric charaters only, at least 8").showAndWait();
            return;
        }

        var response = registrationService.register(username, password);

        switch (response) {
            case SERVER_UNAVAILABLE -> {
                Logger.error("Registration service server unavailable");
                new Alert(Alert.AlertType.ERROR, "Server unavailable").showAndWait();
            }
            case USER_ALREADY_EXISTS -> new Alert(Alert.AlertType.ERROR, "User already exists").showAndWait();
            case SUCCESSFUL -> {
                new Alert(Alert.AlertType.INFORMATION, "Successfull registration").showAndWait();
                App.setUsername(username);
                JavaFxUtil.load(JavaFxUtil.getStageOfEvent(actionEvent), "FXML/menu.fxml");
            }
        }
    }
}
