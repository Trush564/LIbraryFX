package org.example.libraryfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.libraryfx.model.UserSession;
import org.example.libraryfx.service.HistoryService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class HistoryController {
    @FXML
    private Button historyButton;
    
    @FXML
    private Button logoutButton;
    
    @FXML
    private Button searchNavButton;
    
    @FXML
    private Button applicationsButton;
    
    @FXML
    private Button rateButton;
    
    @FXML
    private Button reviewsButton;
    
    @FXML
    private Button catalogButton;
    
    @FXML
    private TextField book1Title;
    
    @FXML
    private TextField book1Genre;
    
    @FXML
    private TextField book1Description;
    
    @FXML
    private TextField book1IssueDate;
    
    @FXML
    private TextField book1ReturnDate;
    
    @FXML
    private TextField book2Title;
    
    @FXML
    private TextField book2Genre;
    
    @FXML
    private TextField book2Description;
    
    @FXML
    private TextField book2IssueDate;
    
    @FXML
    private TextField book2ReturnDate;
    
    @FXML
    private TextField book3Title;
    
    @FXML
    private TextField book3Genre;
    
    @FXML
    private TextField book3Description;
    
    @FXML
    private TextField book3IssueDate;
    
    @FXML
    private TextField book3ReturnDate;
    
    @FXML
    private void initialize() {
        highlightActiveButton(historyButton);
        loadHistory();
    }
    
    private void highlightActiveButton(Button button) {
        // Скидаємо стилі для всіх кнопок
        if (historyButton != null) {
            historyButton.setStyle("-fx-background-color: #F4E4BC; -fx-text-fill: #654321; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: #CCCCCC; -fx-border-width: 1px; -fx-border-radius: 3px;");
            historyButton.setFocusTraversable(false);
        }
        if (searchNavButton != null) {
            searchNavButton.setStyle("-fx-background-color: #F4E4BC; -fx-text-fill: #654321; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: #CCCCCC; -fx-border-width: 1px; -fx-border-radius: 3px;");
            searchNavButton.setFocusTraversable(false);
        }
        if (applicationsButton != null) {
            applicationsButton.setStyle("-fx-background-color: #F4E4BC; -fx-text-fill: #654321; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: #CCCCCC; -fx-border-width: 1px; -fx-border-radius: 3px;");
            applicationsButton.setFocusTraversable(false);
        }
        if (rateButton != null) {
            rateButton.setStyle("-fx-background-color: #F4E4BC; -fx-text-fill: #654321; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: #CCCCCC; -fx-border-width: 1px; -fx-border-radius: 3px;");
            rateButton.setFocusTraversable(false);
        }
        if (reviewsButton != null) {
            reviewsButton.setStyle("-fx-background-color: #F4E4BC; -fx-text-fill: #654321; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: #CCCCCC; -fx-border-width: 1px; -fx-border-radius: 3px;");
            reviewsButton.setFocusTraversable(false);
        }
        if (logoutButton != null) {
            logoutButton.setStyle("-fx-background-color: #F4E4BC; -fx-text-fill: #654321; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: #CCCCCC; -fx-border-width: 1px; -fx-border-radius: 3px;");
            logoutButton.setFocusTraversable(false);
        }
        if (catalogButton != null) {
            catalogButton.setStyle("-fx-background-color: #F4E4BC; -fx-text-fill: #654321; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: #CCCCCC; -fx-border-width: 1px; -fx-border-radius: 3px;");
            catalogButton.setFocusTraversable(false);
        }
        // Виділяємо активну кнопку
        if (button != null) {
            button.setStyle("-fx-background-color: #654321; -fx-text-fill: #F4E4BC; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 3px; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            button.setFocusTraversable(false);
        }
    }
    
    private void loadHistory() {
        UserSession session = HelloApplication.getCurrentUser();
        if (session == null) return;
        
        List<Map<String, Object>> history = HistoryService.getUserHistory(session.getUserId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        TextField[] titles = {book1Title, book2Title, book3Title};
        TextField[] genres = {book1Genre, book2Genre, book3Genre};
        TextField[] descriptions = {book1Description, book2Description, book3Description};
        TextField[] issueDates = {book1IssueDate, book2IssueDate, book3IssueDate};
        TextField[] returnDates = {book1ReturnDate, book2ReturnDate, book3ReturnDate};
        
        for (int i = 0; i < Math.min(history.size(), 3); i++) {
            Map<String, Object> record = history.get(i);
            if (titles[i] != null) titles[i].setText((String) record.get("title"));
            if (genres[i] != null) genres[i].setText((String) record.get("genre"));
            if (descriptions[i] != null) {
                String author = (String) record.get("author");
                descriptions[i].setText(author != null ? author : "");
            }
            if (issueDates[i] != null) {
                java.sql.Date issueDate = (java.sql.Date) record.get("issueDate");
                issueDates[i].setText(issueDate != null ? dateFormat.format(issueDate) : "");
            }
            if (returnDates[i] != null) {
                java.sql.Date returnDate = (java.sql.Date) record.get("returnDate");
                returnDates[i].setText(returnDate != null ? dateFormat.format(returnDate) : "Ще не повернено");
            }
        }
    }
    
    @FXML
    protected void onHistoryButtonClick() throws IOException {
        // Вже на сторінці історії, просто перезавантажуємо для оновлення даних
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("history.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) historyButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Історія книг");
    }
    
    @FXML
    protected void onSearchButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("search.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) searchNavButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Пошук книги");
    }
    
    @FXML
    protected void onCatalogButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("catalog.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) searchNavButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Каталог книг");
    }
    
    @FXML
    protected void onApplicationsButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("applications.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) applicationsButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Подані заяви");
    }
    
    @FXML
    protected void onReserveButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reserve.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) applicationsButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Забронювати");
    }
    
    @FXML
    protected void onRateButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("rate.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) rateButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Оцінити книгу");
    }
    
    @FXML
    protected void onReviewsButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reviews.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) reviewsButton.getScene().getWindow();
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
        Stage stage = (Stage) historyButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Профіль");
    }
}
