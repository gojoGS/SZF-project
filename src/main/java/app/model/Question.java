package app.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "book")
public class Question {
    private @Getter
    String question;
    private @Getter
    Map<AnswerId, String> answerMapping;
    private @Getter
    Map<AnswerId, Double> frequencyMapping;
    private @Getter
    AnswerId correctId;
    private @Getter
    Theme theme;
}
