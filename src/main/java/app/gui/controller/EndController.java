package app.gui.controller;

import app.App;
import app.gui.view.LeaderBoardView;
import app.service.LeaderBoardGetService;
import app.service.WebLeaderBoardGetService;
import app.util.JavaFxUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class EndController {
    LeaderBoardGetService service;

    @FXML
    private void initialize() {
        service = new WebLeaderBoardGetService("leaderboard");
    }

    public void onLeaderboard(ActionEvent actionEvent) {
        JavaFxUtil.load(JavaFxUtil.getStageOfEvent(actionEvent), "FXML/leaderboard.fxml", new LeaderBoardView(service.get(10)));
    }

    public void onMyGames(ActionEvent actionEvent) {
        JavaFxUtil.load(JavaFxUtil.getStageOfEvent(actionEvent), "FXML/leaderboard.fxml", new LeaderBoardView(service.get(App.getUsername(), 10)));
    }

    public void onBackToMenu(ActionEvent actionEvent) {
        JavaFxUtil.load(JavaFxUtil.getStageOfEvent(actionEvent), "FXML/menu.fxml");
    }
}
