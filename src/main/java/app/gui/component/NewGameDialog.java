package app.gui.component;

import app.App;
import app.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Optional;

public class NewGameDialog extends Dialog<GameState> {
    @FXML
    ChoiceBox<Theme> themeChoice;

    @FXML
    ChoiceBox<String> lengthChoice;

    @FXML
    TextField lengthInput;

    public NewGameDialog(Window owner)  {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("FXML/newGameDialog.fxml"));
            loader.setController(this);
            loader.setRoot(new DialogPane());
            DialogPane dialogPane = loader.load();

            initOwner(owner);
            initModality(Modality.APPLICATION_MODAL);

            setResizable(true);
            setTitle("Create new game");
            setDialogPane(dialogPane);

            setResultConverter(this::resultConverter);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void initialize() {
        themeChoice.getItems().add(Theme.ANY);
        themeChoice.getItems().add(Theme.SCIENCE);
        themeChoice.getItems().add(Theme.HISTORY);
        themeChoice.getItems().add(Theme.MOVIES);
        themeChoice.getItems().add(Theme.SPORTS);

        lengthChoice.getItems().add("Classic (15)");
        lengthChoice.getItems().add("Végtelen");
        lengthChoice.getItems().add("Custom");

        lengthChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> lengthInput.setVisible(newValue.equals("Custom")));
    }

    private Optional<Integer> getGameLengthInput() {

        if(lengthInput.getText().equals("")) {
            return Optional.empty();
        }

        Optional<Integer> result;

        try {
            Integer i = Integer.valueOf(lengthInput.getText());
            result = Optional.of(i);

        } catch(NumberFormatException e) {
            result = Optional.empty();
        }

        return result;
    }

    private Optional<Integer> getGameLength() {
        if(lengthChoice.getValue() == null) {
            return Optional.empty();
        }

        return switch (lengthChoice.getValue()) {
            case "Classic (15)" -> Optional.of(15);
            case "Végtelen" -> Optional.of(Integer.MAX_VALUE);
            case "Custom" -> getGameLengthInput();
            default -> Optional.empty();
        };
    }

    private Optional<Theme> getTheme() {
        return Optional.of(themeChoice.getValue());
    }

    private GameState resultConverter(ButtonType buttonType) {
        if(buttonType.equals(ButtonType.CANCEL)) {
            return null;
        }

        if(getGameLength().isEmpty()) {
            return null;
        }

        if(getTheme().isEmpty()) {
            return null;
        }

        int gameLength = getGameLength().get();
        Theme theme = getTheme().get();

        if(theme.equals(Theme.ANY)) {
            return new GameState(new RepetitiveQuestionProvider(App.getQuestions()), theme, gameLength);
        } else {
            return new GameState(new SelectiveRepetitiveQuestionProvider(App.getQuestions(), new ThematicQuestionFilterStrategy(theme)), theme, gameLength);
        }

    }
}
