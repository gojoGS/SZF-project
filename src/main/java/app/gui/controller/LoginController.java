package app.gui.controller;

import app.App;
import app.service.AuthenticationResponse;
import app.service.AuthenticationService;
import app.service.WebAuthenticationService;
import app.util.JavaFxUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

public class LoginController {
    AuthenticationService authenticationService;

    @FXML
    TextField usernameTextField;

    @FXML
    TextField passwordTextField;

    @FXML
    public void initialize() {
        authenticationService = new WebAuthenticationService("auth");
    }

    public void onLogin(ActionEvent actionEvent) {
        var username = usernameTextField.getText();
        var password = passwordTextField.getText();

        var response = authenticationService.authenticate(username, password);

        if (response.equals(AuthenticationResponse.SERVER_UNAVAILABLE)) {
            var alert = new Alert(Alert.AlertType.ERROR, "Server is unavailable");
            alert.showAndWait();
            return;
        }

        if (response.equals(AuthenticationResponse.USER_NOT_FOUND)) {
            var alert = new Alert(Alert.AlertType.ERROR, "User not found.");
            alert.showAndWait();
            return;
        }

        switch (response) {
            case FAILED -> {
                var alert = new Alert(Alert.AlertType.ERROR, "Authentication failed.");
                alert.showAndWait();
            }
            case USER_NOT_FOUND -> {
                var alert = new Alert(Alert.AlertType.ERROR, "User not found.");
                alert.showAndWait();
            }
            case SERVER_UNAVAILABLE -> {
                Logger.error("Authentication server unavailable.");
                var alert = new Alert(Alert.AlertType.ERROR, "Server is unavailable");
                alert.showAndWait();
            }
            case SUCCESSFUL -> {
                Logger.info("Successful authentication.");
                loadMainMenu(JavaFxUtil.getStageOfEvent(actionEvent));
                App.setUsername(username);
            }
        }
    }

    private void loadMainMenu(Stage stage) {
        JavaFxUtil.load(stage, "FXML/menu.fxml");
    }

    public void onRegister(ActionEvent actionEvent) {
        JavaFxUtil.load(JavaFxUtil.getStageOfEvent(actionEvent), "FXML/register.fxml");
    }
}
