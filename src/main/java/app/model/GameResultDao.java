package app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

@Builder
@Data
@AllArgsConstructor
public class GameResultDao {
    private String username;
    private int numberOfQuestions;
    private int gameLength;
    private int helpsRemaining;
    private Theme theme;

    @JsonCreator
    GameResultDao(
            @JsonProperty("username") String username,
            @JsonProperty("numberOfQuestions") int numberOfQuestions,
            @JsonProperty("gameLength") int gameLength,
            @JsonProperty("helpsRemaining") int helpsRemaining,
            @JsonProperty("theme") String theme
    ) {
        this.username = username;
        this.numberOfQuestions =numberOfQuestions;
        this.gameLength = gameLength;
        this.helpsRemaining = helpsRemaining;
        this.theme = Theme.valueOf(StringUtils.upperCase(theme));
    }
}
