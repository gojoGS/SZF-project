package app.model;

import java.util.List;

public interface QuestionFilterStrategy {
    List<Question> filter(List<Question> questions);
}
