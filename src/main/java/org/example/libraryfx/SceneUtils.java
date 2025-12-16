package org.example.libraryfx;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Утилітний клас для роботи зі сценами
 * Забезпечує консистентні розміри для всіх вікон додатку
 */
public class SceneUtils {
    // Стандартні розміри для всіх сцен
    public static final double SCENE_WIDTH = 1000.0;
    public static final double SCENE_HEIGHT = 700.0;
    
    // Мінімальні розміри вікна
    public static final double MIN_WIDTH = 800.0;
    public static final double MIN_HEIGHT = 600.0;
    
    /**
     * Створює нову сцену зі стандартними розмірами
     * @param root кореневий елемент сцени
     * @return нова сцена зі стандартними розмірами
     */
    public static Scene createScene(Parent root) {
        return new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    }
    
    /**
     * Налаштовує вікно зі стандартними параметрами
     * @param stage вікно для налаштування
     * @param title заголовок вікна
     */
    public static void setupStage(Stage stage, String title) {
        stage.setTitle(title);
        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);
        // Не використовуємо sizeToScene(), щоб зберегти фіксовані розміри
    }
    
    /**
     * Перемикає сцену на нову зі стандартними розмірами
     * @param stage поточне вікно
     * @param root новий кореневий елемент
     * @param title заголовок вікна
     */
    public static void switchScene(Stage stage, Parent root, String title) {
        Scene scene = createScene(root);
        stage.setScene(scene);
        setupStage(stage, title);
    }
}

