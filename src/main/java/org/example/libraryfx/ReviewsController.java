package org.example.libraryfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.libraryfx.model.UserSession;
import org.example.libraryfx.service.ReviewService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class ReviewsController {
    @FXML
    private Button reviewsButton;
    
    @FXML
    private Button logoutButton;
    
    @FXML
    private Button catalogButton;
    
    @FXML
    private Label userRoleLabel;
    
    @FXML
    private Label userLoginLabel;
    
    @FXML
    private Label userEmailLabel;
    
    @FXML
    private TextField searchReviewBookTitleField;
    
    @FXML
    private javafx.scene.layout.VBox reviewsContainer;
    
    @FXML
    private TextField review1BookTitle;
    
    @FXML
    private TextField review1User;
    
    @FXML
    private TextField review1Rating;
    
    @FXML
    private TextField review1Text;
    
    @FXML
    private TextField review1Date;
    
    @FXML
    private TextField review2BookTitle;
    
    @FXML
    private TextField review2User;
    
    @FXML
    private TextField review2Rating;
    
    @FXML
    private TextField review2Text;
    
    @FXML
    private TextField review2Date;
    
    @FXML
    private TextField review3BookTitle;
    
    @FXML
    private TextField review3User;
    
    @FXML
    private TextField review3Rating;
    
    @FXML
    private TextField review3Text;
    
    @FXML
    private TextField review3Date;
    
    @FXML
    private void initialize() {
        highlightActiveButton(reviewsButton);
        loadReviews();
        loadUserData();
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
    
    private void highlightActiveButton(Button button) {
        if (reviewsButton != null) {
            reviewsButton.setStyle("-fx-background-color: #F4E4BC; -fx-text-fill: #654321; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: #CCCCCC; -fx-border-width: 1px; -fx-border-radius: 3px;");
            reviewsButton.setFocusTraversable(false);
        }
        if (catalogButton != null) {
            catalogButton.setStyle("-fx-background-color: #F4E4BC; -fx-text-fill: #654321; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: #CCCCCC; -fx-border-width: 1px; -fx-border-radius: 3px;");
            catalogButton.setFocusTraversable(false);
        }
        if (button != null) {
            button.setStyle("-fx-background-color: #654321; -fx-text-fill: #F4E4BC; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 3px; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            button.setFocusTraversable(false);
        }
    }
    
    private void loadReviews() {
        List<Map<String, Object>> reviews = ReviewService.getAllReviews();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        TextField[] bookTitles = {review1BookTitle, review2BookTitle, review3BookTitle};
        TextField[] users = {review1User, review2User, review3User};
        TextField[] ratings = {review1Rating, review2Rating, review3Rating};
        TextField[] texts = {review1Text, review2Text, review3Text};
        TextField[] dates = {review1Date, review2Date, review3Date};
        
        for (int i = 0; i < Math.min(reviews.size(), 3); i++) {
            Map<String, Object> review = reviews.get(i);
            if (bookTitles[i] != null) bookTitles[i].setText((String) review.get("bookTitle"));
            if (users[i] != null) users[i].setText((String) review.get("userLogin"));
            if (ratings[i] != null) {
                int rating = (Integer) review.get("rating");
                ratings[i].setText("★".repeat(rating) + "☆".repeat(5 - rating));
            }
            if (texts[i] != null) texts[i].setText((String) review.get("comment"));
            if (dates[i] != null) {
                java.sql.Timestamp reviewDate = (java.sql.Timestamp) review.get("reviewDate");
                dates[i].setText(reviewDate != null ? dateFormat.format(reviewDate) : "");
            }
        }
    }
    
    @FXML
    protected void onHistoryButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("history.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) reviewsButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Історія книг");
    }
    
    @FXML
    protected void onSearchButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("search.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) reviewsButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Пошук книги");
    }
    
    @FXML
    protected void onCatalogButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("catalog.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) reviewsButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Каталог книг");
    }
    
    @FXML
    protected void onApplicationsButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("applications.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) reviewsButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Подані заяви");
    }
    
    @FXML
    protected void onReserveButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reserve.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) reviewsButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Забронювати");
    }
    
    @FXML
    protected void onRateButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("rate.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) reviewsButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Оцінити книгу");
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
        Stage stage = (Stage) reviewsButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Профіль");
    }
    
    @FXML
    protected void onSearchReviewsButtonClick() {
        String searchText = searchReviewBookTitleField != null ? searchReviewBookTitleField.getText() : "";
        List<Map<String, Object>> allReviews = ReviewService.getAllReviews();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        // Фільтруємо відгуки за назвою книги
        List<Map<String, Object>> filteredReviews = allReviews.stream()
            .filter(review -> {
                String bookTitle = (String) review.get("bookTitle");
                return bookTitle != null && bookTitle.toLowerCase().contains(searchText.toLowerCase());
            })
            .limit(3)
            .toList();
        
        // Очищаємо контейнер
        if (reviewsContainer != null) {
            reviewsContainer.getChildren().clear();
        }
        
        // Відображаємо відфільтровані відгуки
        TextField[] bookTitles = {review1BookTitle, review2BookTitle, review3BookTitle};
        TextField[] users = {review1User, review2User, review3User};
        TextField[] ratings = {review1Rating, review2Rating, review3Rating};
        TextField[] texts = {review1Text, review2Text, review3Text};
        TextField[] dates = {review1Date, review2Date, review3Date};
        
        // Очищаємо всі поля
        for (int i = 0; i < 3; i++) {
            if (bookTitles[i] != null) bookTitles[i].setText("");
            if (users[i] != null) users[i].setText("");
            if (ratings[i] != null) ratings[i].setText("");
            if (texts[i] != null) texts[i].setText("");
            if (dates[i] != null) dates[i].setText("");
        }
        
        // Заповнюємо поля відфільтрованими відгуками
        for (int i = 0; i < Math.min(filteredReviews.size(), 3); i++) {
            Map<String, Object> review = filteredReviews.get(i);
            if (bookTitles[i] != null) bookTitles[i].setText((String) review.get("bookTitle"));
            if (users[i] != null) users[i].setText((String) review.get("userLogin"));
            if (ratings[i] != null) {
                int rating = (Integer) review.get("rating");
                ratings[i].setText("★".repeat(rating) + "☆".repeat(5 - rating));
            }
            if (texts[i] != null) texts[i].setText((String) review.get("comment"));
            if (dates[i] != null) {
                java.sql.Timestamp reviewDate = (java.sql.Timestamp) review.get("reviewDate");
                dates[i].setText(reviewDate != null ? dateFormat.format(reviewDate) : "");
            }
        }
    }
}
