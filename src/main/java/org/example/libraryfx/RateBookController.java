package org.example.libraryfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.libraryfx.model.UserSession;
import org.example.libraryfx.service.ReviewService;

import java.io.IOException;
import java.util.Map;

public class RateBookController {
    @FXML
    private Label star1;
    
    @FXML
    private Label star2;
    
    @FXML
    private Label star3;
    
    @FXML
    private Label star4;
    
    @FXML
    private Label star5;
    
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
    
    @FXML
    private Label userRoleLabel;
    
    @FXML
    private Label userLoginLabel;
    
    @FXML
    private Label userEmailLabel;
    
    @FXML
    private javafx.scene.layout.VBox existingReviewsContainer;
    
    private int currentRating = 0;
    private String selectedBookTitle = ""; // Назва книги, яку оцінюють
    
    @FXML
    private void initialize() {
        highlightActiveButton(rateButton);
        setupStarHandlers();
        updateStarColors(0);
        // Встановлюємо розмиття для фонового зображення
        if (backgroundBookImage != null) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(-0.3);
            backgroundBookImage.setEffect(colorAdjust);
        }
        loadUserData();
        // Якщо назву книги вже передали до ініціалізації, можна завантажити відгуки пізніше через setSelectedBookTitle
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
        Label[] stars = {star1, star2, star3, star4, star5};
        for (int i = 0; i < stars.length; i++) {
            Label star = stars[i];
            if (star == null) continue;
            if (i < rating) {
                star.setText("★");
                star.setStyle("-fx-text-fill: #FFD700; -fx-cursor: hand;");
            } else {
                star.setText("☆");
                star.setStyle("-fx-text-fill: #CCCCCC; -fx-cursor: hand;");
            }
        }
    }
    
    private void highlightActiveButton(Button button) {
        ButtonStyleUtils.setActiveButton(button, rateButton, logoutButton);
    }
    
    private void loadUserData() {
        UserSession session = HelloApplication.getCurrentUser();
        if (session != null && session.getUser() != null) {
            var user = session.getUser();
            if (userRoleLabel != null) {
                String roleText = switch (user.getRole()) {
                    case "student" -> "Студент";
                    case "teacher" -> "Викладач";
                    case "librarian" -> "Бібліотекар";
                    case "admin" -> "Адміністратор";
                    default -> "Користувач";
                };
                userRoleLabel.setText(roleText);
            }
            if (userLoginLabel != null) {
                userLoginLabel.setText(user.getLogin());
            }
            if (userEmailLabel != null) {
                userEmailLabel.setText(user.getEmail());
            }
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
        loadExistingReviews();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Завантажує існуючі відгуки для вибраної книги та показує їх під формою.
     */
    private void loadExistingReviews() {
        if (existingReviewsContainer == null || selectedBookTitle == null || selectedBookTitle.isEmpty()) {
            return;
        }
        existingReviewsContainer.getChildren().clear();
        
        var reviews = ReviewService.getReviews(selectedBookTitle);
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        
        if (reviews.isEmpty()) {
            Label noReviews = new Label("Відгуків для цієї книги ще немає.");
            noReviews.setStyle("-fx-text-fill: #654321;");
            existingReviewsContainer.getChildren().add(noReviews);
            return;
        }
        
        for (Map<String, Object> review : reviews) {
            String user = (String) review.get("userLogin");
            int rating = (Integer) review.get("rating");
            String comment = (String) review.get("comment");
            java.sql.Timestamp ts = (java.sql.Timestamp) review.get("reviewDate");
            String date = ts != null ? dateFormat.format(ts) : "";
            
            Label header = new Label(user + " — " + "★".repeat(rating) + "☆".repeat(5 - rating) + " (" + date + ")");
            header.setStyle("-fx-text-fill: #654321; -fx-font-weight: bold;");
            
            Label text = new Label(comment);
            text.setWrapText(true);
            text.setStyle("-fx-text-fill: #654321;");
            
            javafx.scene.layout.VBox box = new javafx.scene.layout.VBox(3, header, text);
            box.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 8; -fx-background-radius: 5;");
            existingReviewsContainer.getChildren().add(box);
        }
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
