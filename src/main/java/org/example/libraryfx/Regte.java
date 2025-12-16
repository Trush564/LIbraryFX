package org.example.libraryfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.libraryfx.model.User;
import org.example.libraryfx.service.AuthService;

import java.io.IOException;

public class Regte {
    @FXML
    private TextField teacherCodeField;
    
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField surnameField;
    
    @FXML
    private TextField patronymicField;
    
    @FXML
    private TextField phoneField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private TextField loginField;
    
    @FXML
    private TextField passwordField;
    
    @FXML
    private void initialize() {
        // Ініціалізація контролера
    }
    
    @FXML
    protected void onRegisterButtonClick() throws IOException {
        String teacherCode = teacherCodeField.getText();
        String firstName = nameField.getText();
        String lastName = surnameField.getText();
        String middleName = patronymicField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String login = loginField.getText();
        String password = passwordField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || 
            email.isEmpty() || login.isEmpty() || password.isEmpty() || teacherCode.isEmpty()) {
            showAlert("Помилка", "Будь ласка, заповніть всі обов'язкові поля");
            return;
        }

        if (!"111".equals(teacherCode)) {
            showAlert("Помилка", "Невірний код доступу для реєстрації як викладач");
            return;
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMiddleName(middleName);
        user.setPhone(phone);
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(password);

        if (AuthService.register(user, "teacher", teacherCode)) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("regsuc.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) nameField.getScene().getWindow();
            SceneUtils.switchScene(stage, root, "Реєстрація успішна");
        } else {
            showAlert("Помилка", "Не вдалося зареєструвати користувача. Можливо, логін вже використовується.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
