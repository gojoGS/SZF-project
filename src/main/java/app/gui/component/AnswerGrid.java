package app.gui.component;

import app.model.AnswerId;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnswerGrid extends GridPane {
    private final Map<AnswerId, Button> mapping;
    private Map<AnswerId, Double> frequencyMapping;
    private Map<AnswerId, String> answerMapping;

    public AnswerGrid() {
        mapping = new HashMap<>();

        var values = AnswerId.values();
        var valuesIndex = 0;

        for (int i = 0; i <= 1; ++i) {
            for (int j = 0; j <= 1; ++j) {
                var button = new Button(String.format("%s: ", values[valuesIndex]));
                mapping.put(values[valuesIndex], button);
                this.add(button, j, i);

                valuesIndex++;
            }
        }

        setGap(10);
        setSpacing();
        setButtonStyleClass();
        this.setPadding(new Insets(15));
    }

    public AnswerGrid(Map<AnswerId, String> answerMapping, Map<AnswerId, Double> frequencyMapping) {
        this();
        this.answerMapping = answerMapping;
        this.frequencyMapping = frequencyMapping;
        setAnswer();
    }

    private void setSpacing() {
        for(var button: getChildrenNodes()) {
            AnswerGrid.setHgrow(button, Priority.ALWAYS);
            button.setMaxWidth(Double.MAX_VALUE);
        }
    }

    private void setButtonStyleClass() {
        for(var button: getChildrenNodes()) {
            button.getStyleClass().add("answerButton");
        }
    }

    private void setGap(double gap){
        setHgap(gap);
        setVgap(gap);
    }


    private void addStyleClassToButton(AnswerId id, String styleClass) {
        mapping.get(id).getStyleClass().add(styleClass);
    }

    public void setSelectedStyleForButton(AnswerId id) {
        addStyleClassToButton(id, "answerButton_selected");
    }

    public void setRightStyleForButton(AnswerId id) {
        addStyleClassToButton(id, "answerButton_right");
    }

    public void setWrongStyleForButton(AnswerId id) {
        addStyleClassToButton(id, "answerButton_wrong");
    }

    public void setButtonOnActionEventHandler(AnswerId id, EventHandler<ActionEvent> handler) {
        mapping.get(id).setOnAction(handler);
    }

    public void resetStyleForButton(AnswerId id) {
        mapping.get(id).getStyleClass().clear();
        mapping.get(id).getStyleClass().add("answerButton");
    }

    public AnswerId getAnswerIdOfButton(Button button) {
        int col = GridPane.getColumnIndex(button);
        int row = GridPane.getRowIndex(button);

        if (row == 0) {
            if (col == 0) {
                return AnswerId.A;
            } else {
                return AnswerId.B;
            }
        } else {
            if (col == 0) {
                return AnswerId.C;
            } else {
                return AnswerId.D;
            }
        }
    }

    public void setButtonOnActionEventHandler(EventHandler<ActionEvent> handler) {
        for(var id: AnswerId.values()) {
            setButtonOnActionEventHandler(id, handler);
        }
    }

    public List<Button> getChildrenNodes() {
        return new ArrayList<>(mapping.values());
    }

    private void setAnswer(AnswerId id, String answer) {
        mapping.get(id).setText(String.format("%s: %s", id, answer));
    }

    private void toggleFrequency(AnswerId id) {
        mapping.get(id).setText(String.format("%s: %s (%.0f%%)", id, answerMapping.get(id), frequencyMapping.get(id) * 100));
    }

    public void toggleFrequency() {
        for(var id: mapping.keySet()) {
            toggleFrequency(id);
        }
    }

    private void setAnswer() {
        for(var entry: answerMapping.entrySet()) {
            setAnswer(entry.getKey(), entry.getValue());
        }
    }
}
