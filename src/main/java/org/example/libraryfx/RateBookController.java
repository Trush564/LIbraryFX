package org.example.libraryfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.libraryfx.model.UserSession;
import org.example.libraryfx.service.ReviewService;

import java.io.IOException;

public class RateBookController {
    @FXML
    private ImageView star1;
    
    @FXML
    private ImageView star2;
    
    @FXML
    private ImageView star3;
    
    @FXML
    private ImageView star4;
    
    @FXML
    private ImageView star5;
    
    @FXML
    private TextArea reviewTextArea;
    
    @FXML
    private ImageView backgroundBookImage;
    
    @FXML
    private Button submitRateButton;
    
    @FXML
    private Button rateButton;
    
    @FXML
    private Button logoutButton;
    
    private int currentRating = 0;
    private String selectedBookTitle = ""; // Назва книги, яку оцінюють
    
    @FXML
    private void initialize() {
        highlightActiveButton(rateButton);
        setupStarHandlers();
        // Встановлюємо розмиття для фонового зображення
        if (backgroundBookImage != null) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(-0.3);
            backgroundBookImage.setEffect(colorAdjust);
        }
    }
    
    private void setupStarHandlers() {
        if (star1 != null) {
            star1.setOnMouseClicked(e -> handleStarClick(1));
            star1.setOnMouseEntered(e -> handleStarHover(1));
            star1.setOnMouseExited(e -> handleStarExit());
        }
        if (star2 != null) {
            star2.setOnMouseClicked(e -> handleStarClick(2));
            star2.setOnMouseEntered(e -> handleStarHover(2));
            star2.setOnMouseExited(e -> handleStarExit());
        }
        if (star3 != null) {
            star3.setOnMouseClicked(e -> handleStarClick(3));
            star3.setOnMouseEntered(e -> handleStarHover(3));
            star3.setOnMouseExited(e -> handleStarExit());
        }
        if (star4 != null) {
            star4.setOnMouseClicked(e -> handleStarClick(4));
            star4.setOnMouseEntered(e -> handleStarHover(4));
            star4.setOnMouseExited(e -> handleStarExit());
        }
        if (star5 != null) {
            star5.setOnMouseClicked(e -> handleStarClick(5));
            star5.setOnMouseEntered(e -> handleStarHover(5));
            star5.setOnMouseExited(e -> handleStarExit());
        }
    }
    
    private void handleStarClick(int rating) {
        currentRating = rating;
        updateStarColors();
    }
    
    private void handleStarHover(int rating) {
        updateStarColors(rating);
    }
    
    private void handleStarExit() {
        updateStarColors();
    }
    
    private void updateStarColors() {
        updateStarColors(currentRating);
    }
    
    private void updateStarColors(int hoverRating) {
        int rating = hoverRating > 0 ? hoverRating : currentRating;
        // Тут можна встановити різні зображення для заповнених та порожніх зірок
        // Поки що просто оновлюємо стилі
    }
    
    private void highlightActiveButton(Button button) {
        if (rateButton != null) {
            rateButton.setStyle("-fx-background-color: #F4E4BC; -fx-text-fill: #654321; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: #CCCCCC; -fx-border-width: 1px; -fx-border-radius: 3px;");
            rateButton.setFocusTraversable(false);
        }
        if (button != null) {
            button.setStyle("-fx-background-color: #654321; -fx-text-fill: #F4E4BC; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 3px; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            button.setFocusTraversable(false);
        }
    }
    
    @FXML
    protected void onSubmitRateClick() {
        UserSession session = HelloApplication.getCurrentUser();
        if (session == null) {
            showAlert("Помилка", "Будь ласка, увійдіть в систему");
            return;
        }

        if (currentRating == 0) {
            showAlert("Помилка", "Будь ласка, оберіть оцінку");
            return;
        }

        if (selectedBookTitle.isEmpty()) {
            showAlert("Помилка", "Книга не обрана");
            return;
        }

        String review = reviewTextArea.getText();
        if (review.isEmpty()) {
            review = "Без коментаря";
        }

        boolean success = ReviewService.addReview(
            session.getLogin(),
            selectedBookTitle,
            currentRating,
            review
        );

        if (success) {
            showAlert("Успіх", "Відгук збережено успішно!");
            reviewTextArea.clear();
            currentRating = 0;
            updateStarColors();
        } else {
            showAlert("Помилка", "Не вдалося зберегти відгук");
        }
    }

    public void setSelectedBookTitle(String bookTitle) {
        this.selectedBookTitle = bookTitle;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    protected void onHistoryButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("history.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) submitRateButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Історія книг");
    }
    
    @FXML
    protected void onSearchButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("search.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) submitRateButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Пошук книги");
    }
    
    @FXML
    protected void onApplicationsButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("applications.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) submitRateButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Подані заяви");
    }
    
    @FXML
    protected void onReserveButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reserve.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) submitRateButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Забронювати");
    }
    
    @FXML
    protected void onRateButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("rate.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) submitRateButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Оцінити книгу");
    }
    
    @FXML
    protected void onReviewsButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reviews.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) submitRateButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Відгуки");
    }
    
    @FXML
    protected void onLogoutButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Бібліотека");
    }
    
    @FXML
    protected void onBackToMainButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainprofile.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) rateButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Профіль");
    }
}
