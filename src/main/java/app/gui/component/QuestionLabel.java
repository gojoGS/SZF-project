package app.gui.component;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;

public class QuestionLabel extends Label {
    public QuestionLabel() {
        this("");
    }

    public QuestionLabel(String question) {
        super(question);
        setWrapText(true);
        getStyleClass().add("questionLabel");
        setContentDisplay(ContentDisplay.CENTER);
    }
}
