package org.example.libraryfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.libraryfx.database.DatabaseInitializer;
import org.example.libraryfx.model.UserSession;

import java.io.IOException;

public class HelloApplication extends Application {
    private static UserSession currentUser;

    @Override
    public void start(Stage stage) throws IOException {
        // Ініціалізуємо базу даних
        DatabaseInitializer.initialize();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = SceneUtils.createScene(fxmlLoader.load());
        SceneUtils.setupStage(stage, "Бібліотека");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static UserSession getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(UserSession user) {
        currentUser = user;
    }
}
