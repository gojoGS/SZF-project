package app;

import app.model.Question;
import app.model.QuestionWrapper;
import app.util.JaxbUtil;
import jakarta.xml.bind.JAXBException;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;

import java.util.List;

import static java.lang.System.exit;

public class App extends Application {
    private static @Getter
    @Setter
    String username;

    private static @Getter
    final String backendBaseUrl = "http://ec2-18-116-65-236.us-east-2.compute.amazonaws.com:8001";

    private static @Getter
    List<Question> questions;

    private static void loadQuestions() throws JAXBException {
        var questionWrapper = JaxbUtil.fromXML(QuestionWrapper.class, App.class.getClassLoader().getResourceAsStream("assets/questions.xml"));
        questions = questionWrapper.getQuestions();
    }
    private static BooleanProperty isDarkThemeProperty = new SimpleBooleanProperty(false);


    private static void loadStylesheets() {
        var windows = Window.getWindows();

        for (var window : windows) {
            loadStylesheets(window.getScene());
        }
    }

    public static void loadStylesheets(Scene scene) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(App.class.getClassLoader().getResource("FXML/CSS/style.css").toExternalForm());

        if (isDarkThemeProperty.get()) {
            scene.getStylesheets().add(App.class.getClassLoader().getResource("FXML/CSS/dark.css").toExternalForm());
        } else {
            scene.getStylesheets().add(App.class.getClassLoader().getResource("FXML/CSS/light.css").toExternalForm());
        }
    }

    public static void switchTheme() {
        isDarkThemeProperty.set(!isDarkThemeProperty.get());
    }


    public static void main(String[] args) {
        isDarkThemeProperty.addListener(e -> loadStylesheets());
        try {
            loadQuestions();
            Logger.info("Loaded questions.xml");
        } catch (JAXBException e) {
            Logger.error("Failed to load questions.xml");
            exit(-1);
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/login.fxml"));
        var scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();

        loadStylesheets();
    }
}
