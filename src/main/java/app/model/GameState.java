package app.model;

import app.App;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameState {
    private final QuestionProvider provider;
    private @Getter
    ObjectProperty<Question> currentQuestion;

    private final int numberOfQuestions;
    private @Getter int gameLength;

    private final Set<Help> helpSet;
    private final Theme gameTheme;

    @Builder
    public GameState(QuestionProvider provider, Theme theme, int numberOfQuestions) {
        this.provider = provider;
        this.gameTheme = theme;
        this.numberOfQuestions = numberOfQuestions;

        helpSet = new HashSet<>();
        helpSet.add(Help.AUDIENCE);
        helpSet.add(Help.HALF);
        helpSet.add(Help.PHONE);

        gameLength = 1;

        currentQuestion = new SimpleObjectProperty<>(provider.get());
    }

    public boolean isWinner() {
        return gameLength > numberOfQuestions;
    }

    public boolean isAnswerChoiceCorrect(AnswerId id) {
        return currentQuestion.get().getCorrectId().equals(id);
    }

    public boolean isHelpAvailable(Help help) {
        return helpSet.contains(help);
    }

    public void removeHelp(Help help) {
        helpSet.remove(help);
    }

    public boolean nextTurn() {
        ++gameLength;
        currentQuestion.set(provider.get());
        return isWinner();
    }



    public Map<AnswerId, String> getAnswerMapping() {
        return currentQuestion.get().getAnswerMapping();
    }

    public Map<AnswerId, Double> getFrequencyMapping() {
        return currentQuestion.get().getFrequencyMapping();
    }

    public String getQuestion() {

        return String.format("%s: %s", gameLength, currentQuestion.get().getQuestion());
    }

    public GameResultDao getGameResult() {
        return GameResultDao
                .builder()
                .gameLength(gameLength - 1)
                .helpsRemaining(helpSet.size())
                .theme(gameTheme)
                .numberOfQuestions(numberOfQuestions)
                .username(App.getUsername())
                .build();
    }
}
