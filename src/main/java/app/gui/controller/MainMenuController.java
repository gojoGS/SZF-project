package app.gui.controller;

import app.App;
import app.gui.component.NewGameDialog;
import app.gui.view.LeaderBoardView;
import app.service.LeaderBoardGetService;
import app.service.WebLeaderBoardGetService;
import app.util.JavaFxUtil;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import org.tinylog.Logger;

import java.util.ArrayList;

public class MainMenuController {
    LeaderBoardGetService service = new WebLeaderBoardGetService("leaderboard");


    public void onSwitchTheme(ActionEvent actionEvent) {
        App.switchTheme();
    }

    public void onLogOut(ActionEvent actionEvent) {
        App.setUsername(null);
        JavaFxUtil.load(JavaFxUtil.getStageOfEvent(actionEvent), "FXML/login.fxml");
    }

    public void onTop10(ActionEvent actionEvent) {
        var results = service.get(10);

        if (results.isEmpty()) {
            JavaFxUtil.load(JavaFxUtil.getStageOfEvent(actionEvent), "FXML/leaderboard.fxml", new LeaderBoardView(new ArrayList<>()));
        } else {
            JavaFxUtil.load(JavaFxUtil.getStageOfEvent(actionEvent), "FXML/leaderboard.fxml", new LeaderBoardView(results));
        }
    }

    public void onMyGames(ActionEvent actionEvent) {
        var results = service.get(App.getUsername(), 10);

        if (results.isEmpty()) {
            JavaFxUtil.load(JavaFxUtil.getStageOfEvent(actionEvent), "FXML/leaderboard.fxml", new LeaderBoardView(new ArrayList<>()));
        } else {
            JavaFxUtil.load(JavaFxUtil.getStageOfEvent(actionEvent), "FXML/leaderboard.fxml", new LeaderBoardView(results));
        }
    }

    public void onNewGame(ActionEvent actionEvent) {
        var newGameDialog = new NewGameDialog(((Node) actionEvent.getTarget()).getScene().getWindow());
        var result = newGameDialog.showAndWait();

        if(result.isEmpty()) {
            return;
        }

        JavaFxUtil.load(JavaFxUtil.getStageOfEvent(actionEvent), "FXML/game.fxml", new GameController(result.get()));
    }
}
