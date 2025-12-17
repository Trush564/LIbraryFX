
package org.example.libraryfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.libraryfx.database.DatabaseConnection;
import org.example.libraryfx.model.UserSession;
import org.example.libraryfx.service.ReservationService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReserveController {
    @FXML
    private Button historyButton;

    @FXML
    private Button searchNavButton;

    @FXML
    private Button reserveButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button rateButton;

    @FXML
    private Button reviewsButton;

    @FXML
    private Button applicationsButton;
    
    @FXML
    private Button catalogButton;
    
    @FXML
    private Label userRoleLabel;
    
    @FXML
    private Label userLoginLabel;
    
    @FXML
    private Label userEmailLabel;
    
    @FXML
    private TextField bookTitleField;

    @FXML
    private TextField bookingDateField;

    @FXML
    private Button submitBookingButton;

    @FXML
    private void initialize() {
        highlightActiveButton(reserveButton);
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
            historyButton, searchNavButton, reserveButton, applicationsButton, 
            rateButton, reviewsButton, catalogButton, logoutButton);
    }

    @FXML
    protected void onSubmitBooking() {
        UserSession session = HelloApplication.getCurrentUser();
        if (session == null) {
            showAlert("Помилка", "Будь ласка, увійдіть в систему");
            return;
        }

        String bookTitle = bookTitleField.getText();
        String bookingDate = bookingDateField.getText();

        if (bookTitle.isEmpty() || bookingDate.isEmpty()) {
            showAlert("Помилка", "Будь ласка, заповніть всі поля");
            return;
        }

        try {
            // Знаходимо bookId за назвою
            int bookId = findBookIdByTitle(bookTitle);
            if (bookId == -1) {
                showAlert("Помилка", "Книгу не знайдено");
                return;
            }

            // Створюємо бронювання
            boolean success = ReservationService.createReservation(
                session.getUserId(),
                bookId,
                bookingDate,
                false,
                null
            );

            if (success) {
                showAlert("Успіх", "Бронювання створено успішно!");
                bookTitleField.clear();
                bookingDateField.clear();
            } else {
                showAlert("Помилка", "Не вдалося створити бронювання");
            }
        } catch (Exception e) {
            showAlert("Помилка", "Помилка при створенні бронювання: " + e.getMessage());
        }
    }

    private int findBookIdByTitle(String title) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT id FROM books WHERE LOWER(title) = LOWER(?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
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
        Stage stage = (Stage) reserveButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Профіль");
    }
}

