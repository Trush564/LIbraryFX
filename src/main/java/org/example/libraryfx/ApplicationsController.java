package org.example.libraryfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.libraryfx.model.UserSession;
import org.example.libraryfx.service.ReservationService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class ApplicationsController {
    @FXML
    private Button historyButton;
    
    @FXML
    private Button searchNavButton;
    
    @FXML
    private Button applicationsButton;
    
    @FXML
    private Button logoutButton;
    
    @FXML
    private Button rateButton;
    
    @FXML
    private Button reviewsButton;
    
    @FXML
    private Button catalogButton;
    
    @FXML
    private TextField app1Title;
    
    @FXML
    private TextField app1Genre;
    
    @FXML
    private TextField app1Description;
    
    @FXML
    private TextField app1Status;
    
    @FXML
    private TextField app1BookingDate;
    
    @FXML
    private TextField app2Title;
    
    @FXML
    private TextField app2Genre;
    
    @FXML
    private TextField app2Description;
    
    @FXML
    private TextField app2Status;
    
    @FXML
    private TextField app2BookingDate;
    
    @FXML
    private TextField app3Title;
    
    @FXML
    private TextField app3Genre;
    
    @FXML
    private TextField app3Description;
    
    @FXML
    private TextField app3Status;
    
    @FXML
    private TextField app3BookingDate;
    
    @FXML
    private void initialize() {
        highlightActiveButton(applicationsButton);
        loadApplications();
    }
    
    private void highlightActiveButton(Button button) {
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
        if (button != null) {
            button.setStyle("-fx-background-color: #654321; -fx-text-fill: #F4E4BC; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 3px; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            button.setFocusTraversable(false);
        }
    }
    
    private void loadApplications() {
        UserSession session = HelloApplication.getCurrentUser();
        if (session == null) return;
        
        List<Map<String, Object>> reservations = ReservationService.getUserReservations(session.getUserId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        TextField[] titles = {app1Title, app2Title, app3Title};
        TextField[] genres = {app1Genre, app2Genre, app3Genre};
        TextField[] descriptions = {app1Description, app2Description, app3Description};
        TextField[] statuses = {app1Status, app2Status, app3Status};
        TextField[] bookingDates = {app1BookingDate, app2BookingDate, app3BookingDate};
        
        for (int i = 0; i < Math.min(reservations.size(), 3); i++) {
            Map<String, Object> reservation = reservations.get(i);
            if (titles[i] != null) titles[i].setText((String) reservation.get("title"));
            if (genres[i] != null) genres[i].setText((String) reservation.get("genre"));
            if (descriptions[i] != null) {
                String author = (String) reservation.get("author");
                descriptions[i].setText(author != null ? author : "");
            }
            if (statuses[i] != null) {
                String status = (String) reservation.get("status");
                String statusText = switch (status != null ? status : "") {
                    case "PENDING" -> "В очікуванні";
                    case "APPROVED" -> "Схвалено";
                    case "REJECTED" -> "Відхилено";
                    default -> status != null ? status : "";
                };
                statuses[i].setText(statusText);
            }
            if (bookingDates[i] != null) {
                java.sql.Date reservationDate = (java.sql.Date) reservation.get("reservationDate");
                bookingDates[i].setText(reservationDate != null ? dateFormat.format(reservationDate) : "");
            }
        }
    }
    
    @FXML
    protected void onHistoryButtonClick() throws IOException {
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
    protected void onReserveButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reserve.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) searchNavButton.getScene().getWindow();
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
        Stage stage = (Stage) applicationsButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Профіль");
    }
}
