package app.model;

import java.util.List;

public class SelectiveRepetitiveQuestionProvider extends RepetitiveQuestionProvider {
    public SelectiveRepetitiveQuestionProvider(List<Question> questionList, QuestionFilterStrategy strategy) {
        super(strategy.filter(questionList));
    }
}
