package app.model;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ThematicQuestionFilterStrategy implements QuestionFilterStrategy {
    Theme theme;

    @Override
    public List<Question> filter(List<Question> questions) {
        return questions
                .stream()
                .filter(q -> q.getTheme().equals(theme))
                .toList();
    }
}
