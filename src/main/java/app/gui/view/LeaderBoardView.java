package app.gui.view;

import app.model.GameResultDao;
import app.model.Theme;
import app.util.JavaFxUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LeaderBoardView implements Initializable {
    @FXML
    public TableColumn<GameResultDao, String> username;
    @FXML
    public TableColumn<GameResultDao, Integer> questions;
    @FXML
    public TableColumn<GameResultDao, Integer> gameLength;
    @FXML
    public TableColumn<GameResultDao, Integer> helpsRemaining;
    @FXML
    public TableColumn<GameResultDao, Theme> theme;
    @FXML
    TableView<GameResultDao> resultView;

    private List<GameResultDao> results;

    public LeaderBoardView(List<GameResultDao> results) {
        this.results = results;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        questions.setCellValueFactory(new PropertyValueFactory<>("numberOfQuestions"));
        gameLength.setCellValueFactory(new PropertyValueFactory<>("gameLength"));
        helpsRemaining.setCellValueFactory(new PropertyValueFactory<>("helpsRemaining"));
        theme.setCellValueFactory(new PropertyValueFactory<>("theme"));

        resultView.setItems(FXCollections.observableList(results));
    }

    public void onBack(ActionEvent actionEvent) {
        JavaFxUtil.load(JavaFxUtil.getStageOfEvent(actionEvent), "FXML/menu.fxml");
    }
}
