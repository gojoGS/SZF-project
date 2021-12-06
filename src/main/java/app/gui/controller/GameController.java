package app.gui.controller;

import app.gui.component.AnswerGrid;
import app.gui.component.QuestionLabel;
import app.model.AnswerId;
import app.model.GameState;
import app.model.Help;
import app.model.Question;
import app.service.LeaderBoardPostService;
import app.service.WebLeaderBoardPostService;
import app.util.JavaFxUtil;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private GameState gameState;

    @FXML
    VBox vbox;

    @FXML
    Button giveUpButton;

    @FXML
    Button audienceButton;

    @FXML
    Button phoneButton;

    @FXML
    Button halfButton;

    QuestionLabel questionLabel;
    AnswerGrid answerGrid;

    public GameController(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var question = gameState.getCurrentQuestion().get();
        questionLabel = new QuestionLabel(gameState.getQuestion());
        answerGrid = new AnswerGrid(question.getAnswerMapping(), question.getFrequencyMapping());

        vbox.getChildren().add(questionLabel);
        vbox.getChildren().add(answerGrid);

        gameState.getCurrentQuestion().addListener(this::onQuestionChange);
        audienceButton.setOnAction(this::onAudience);
        halfButton.setOnAction(this::onHalf);
        phoneButton.setOnAction(this::onPhone);
        giveUpButton.setOnAction(this::onGiveUp);
        answerGrid.setButtonOnActionEventHandler(this::onAnswerChoice);

    }

    private void onGiveUp(ActionEvent actionEvent) {
        new Alert(Alert.AlertType.INFORMATION, "You gave up").showAndWait();
        onEnd(JavaFxUtil.getStageOfEvent(actionEvent));
    }

    private void onPhone(ActionEvent actionEvent) {
//        new Alert(Alert.AlertType.INFORMATION, String.format("I think it's %s", gameState.getCurrentQuestion().get().getCorrectId())).showAndWait();
//        gameState.removeHelp(Help.PHONE);
    }

    private void onHalf(ActionEvent actionEvent) {
//        AnswerId other = AnswerId.A;
//
//        AnswerId correct = gameState.getCurrentQuestion().get().getCorrectId();
//
//        if (other.equals(correct)) {
//            other = AnswerId.B;
//        }
//
//        new Alert(Alert.AlertType.INFORMATION, String.format("%s and %s", correct, other)).showAndWait();
//        gameState.removeHelp(Help.HALF);
    }

    private void onAudience(ActionEvent actionEvent) {
//        answerGrid.toggleFrequency();
//        gameState.removeHelp(Help.AUDIENCE);
    }

    private void onAnswerChoice(ActionEvent actionEvent) {
        AnswerId choice = answerGrid.getAnswerIdOfButton((Button) actionEvent.getSource());
        Stage stage = JavaFxUtil.getStageOfEvent(actionEvent);

        answerGrid.setSelectedStyleForButton(choice);

        Timeline timeline = new Timeline();

        if(!gameState.isAnswerChoiceCorrect(choice)) {
            timeline.getKeyFrames().add(new KeyFrame(
                    Duration.seconds(2),
                    event -> {
                        var alert =new Alert(Alert.AlertType.WARNING, "Wrong answer; You lost");

                        alert.setOnHidden(e -> onEnd(stage));

                        alert.show();
                    }
            ));
        } else {
            timeline.getKeyFrames().add(new KeyFrame(
                    Duration.seconds(2),
                    event -> {
                        boolean isPlayerWinner = gameState.nextTurn();

                        if(isPlayerWinner) {
                            var alert = new Alert(Alert.AlertType.INFORMATION, "You won");
                            alert.setOnHidden(e -> onEnd(stage));
                            alert.show();
                        }
                    }
            ));
        }

        timeline.play();
    }

    private void onQuestionChange(ObservableValue<? extends Question> observable, Question oldValue, Question newValue) {
        answerGrid = new AnswerGrid(newValue.getAnswerMapping(), newValue.getFrequencyMapping());
        questionLabel = new QuestionLabel(String.format("%s: %s", gameState.getGameLength(), newValue.getQuestion()));

        vbox.getChildren().set(1, questionLabel);
        vbox.getChildren().set(2, answerGrid);
        answerGrid.setButtonOnActionEventHandler(this::onAnswerChoice);


        if(!gameState.isHelpAvailable(Help.PHONE)) {
             phoneButton.setDisable(true);
        }
        if(!gameState.isHelpAvailable(Help.AUDIENCE)) {
            audienceButton.setDisable(true);
        }
        if(!gameState.isHelpAvailable(Help.HALF)) {
            halfButton.setDisable(true);
        }
    }

    private void onEnd(Stage nextStage) {
//        LeaderBoardPostService service = new WebLeaderBoardPostService("leaderboard");
//        service.post(gameState.getGameResult());
//
//        JavaFxUtil.load(nextStage, "FXML/end.fxml");
    }
}
