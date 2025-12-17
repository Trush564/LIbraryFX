package org.example.libraryfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private Label userRoleLabel;
    
    @FXML
    private Label userLoginLabel;
    
    @FXML
    private Label userEmailLabel;
    
    @FXML
    private VBox applicationsContainer;
    
    @FXML
    private void initialize() {
        highlightActiveButton(applicationsButton);
        loadApplications();
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
        ButtonStyleUtils.setActiveButton(button, 
            historyButton, searchNavButton, catalogButton, applicationsButton, 
            rateButton, reviewsButton, logoutButton);
    }
    
    private void loadApplications() {
        if (applicationsContainer == null) return;
        applicationsContainer.getChildren().clear();
        
        UserSession session = HelloApplication.getCurrentUser();
        if (session == null) return;
        
        List<Map<String, Object>> reservations = ReservationService.getUserReservations(session.getUserId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        for (Map<String, Object> reservation : reservations) {
            HBox applicationCard = createApplicationCard(reservation, dateFormat);
            applicationsContainer.getChildren().add(applicationCard);
        }
    }
    
    private HBox createApplicationCard(Map<String, Object> reservation, SimpleDateFormat dateFormat) {
        HBox card = new HBox(15);
        card.setAlignment(Pos.CENTER_LEFT);
        card.getStyleClass().add("book-card");
        card.setPrefWidth(Double.MAX_VALUE);
        
        // Обкладинка книги зліва - завжди використовуємо book.jpg з resources/org/example/libraryfx/images
        ImageView coverView = new ImageView();
        try {
            Image image = new Image(ApplicationsController.class.getResourceAsStream("/org/example/libraryfx/images/book.jpg"));
            coverView.setImage(image);
        } catch (Exception e) {
            System.err.println("Failed to load book image: " + e.getMessage());
        }
        coverView.setFitHeight(120);
        coverView.setFitWidth(80);
        coverView.setPreserveRatio(true);
        
        // Деталі справа
        VBox detailsBox = new VBox(8);
        detailsBox.setAlignment(Pos.CENTER_LEFT);
        
        Label titleLabel = new Label("Назва: " + (reservation.get("title") != null ? reservation.get("title").toString() : ""));
        titleLabel.getStyleClass().add("book-card-label");
        
        Label genreLabel = new Label("Жанр: " + (reservation.get("genre") != null ? reservation.get("genre").toString() : ""));
        genreLabel.getStyleClass().add("book-card-label");
        
        Label authorLabel = new Label("Опис: " + (reservation.get("author") != null ? reservation.get("author").toString() : ""));
        authorLabel.getStyleClass().add("book-card-label");
        
        String status = (String) reservation.get("status");
        String statusText = switch (status != null ? status : "") {
            case "PENDING" -> "В очікуванні";
            case "APPROVED" -> "Схвалено";
            case "REJECTED" -> "Відхилено";
            default -> status != null ? status : "";
        };
        Label statusLabel = new Label("Статус: " + statusText);
        statusLabel.getStyleClass().add("book-card-label");
        
        java.sql.Date reservationDate = (java.sql.Date) reservation.get("reservationDate");
        Label bookingDateLabel = new Label("Дата бронювання: " + (reservationDate != null ? dateFormat.format(reservationDate) : ""));
        bookingDateLabel.getStyleClass().add("book-card-label");
        
        detailsBox.getChildren().addAll(titleLabel, genreLabel, authorLabel, statusLabel, bookingDateLabel);
        
        card.getChildren().addAll(coverView, detailsBox);
        return card;
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
