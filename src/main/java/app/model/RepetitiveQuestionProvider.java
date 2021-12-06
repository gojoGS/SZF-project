package app.model;

import java.util.List;

public class RepetitiveQuestionProvider implements QuestionProvider {
    protected final List<Question> questionList;
    protected int index;

    public RepetitiveQuestionProvider(List<Question> questionList) {
        if (questionList.isEmpty()) {
            throw new IllegalArgumentException("questionList cant be empt");
        }

        this.questionList = questionList;
        index = 0;
    }

    @Override
    public Question get() {
        index %= questionList.size();
        index++;

        return questionList.get(index - 1);
    }
}
