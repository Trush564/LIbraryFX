package org.example.libraryfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.libraryfx.model.UserSession;
import org.example.libraryfx.model.Book;
import org.example.libraryfx.service.BookService;

import java.io.IOException;
import java.util.List;

public class SearchController {
    @FXML
    private Pane searchPane;
    
    @FXML
    private TextField searchTextField;
    
    @FXML
    private Button searchActionButton;
    
    @FXML
    private Button logoutButton;
    
    @FXML
    private Button historyButton;
    
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
    private VBox searchResultsContainer;
    
    @FXML
    private Label userRoleLabel;
    
    @FXML
    private Label userLoginLabel;
    
    @FXML
    private Label userEmailLabel;
    
    @FXML
    private void initialize() {
        highlightActiveButton(searchNavButton);
        loadUserData();
    }
    
    private void highlightActiveButton(Button button) {
        ButtonStyleUtils.setActiveButton(button, 
            historyButton, searchNavButton, catalogButton, applicationsButton, 
            rateButton, reviewsButton, logoutButton);
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
    protected void onSearchActionButtonClick() {
        String searchText = searchTextField.getText();
        if (searchText == null || searchText.trim().isEmpty()) {
            return;
        }
        
        List<Book> books = BookService.searchBooks(searchText);
        displaySearchResults(books);
    }
    
    private void displaySearchResults(List<Book> books) {
        if (searchResultsContainer == null) return;
        searchResultsContainer.getChildren().clear();
        
        if (books.isEmpty()) {
            Label noResultsLabel = new Label("Книг не знайдено.");
            noResultsLabel.setStyle("-fx-text-fill: #654321; -fx-font-size: 16px;");
            searchResultsContainer.getChildren().add(noResultsLabel);
            return;
        }
        
        for (Book book : books) {
            HBox resultBox = new HBox(10);
            resultBox.getStyleClass().add("search-result-card");
            resultBox.setPrefWidth(Double.MAX_VALUE);
            VBox.setMargin(resultBox, new javafx.geometry.Insets(0, 0, 10, 0));
            
            ImageView cover = new ImageView();
            try {
                // Завжди використовуємо book.jpg для всіх книг з resources/org/example/libraryfx/images
                String imagePath = "/org/example/libraryfx/images/book.jpg";
                Image image = new Image(SearchController.class.getResourceAsStream(imagePath));
                cover.setImage(image);
            } catch (Exception e) {
                System.err.println("Failed to load book image: " + e.getMessage());
            }
            cover.setFitHeight(100);
            cover.setFitWidth(70);
            cover.setPreserveRatio(true);
            
            VBox detailsBox = new VBox(5);
            Label titleLabel = new Label("Назва: " + book.getTitle());
            titleLabel.getStyleClass().add("book-card-label");
            Label authorLabel = new Label("Автор: " + book.getAuthor());
            authorLabel.getStyleClass().add("book-card-label");
            Label genreLabel = new Label("Жанр: " + book.getGenre());
            genreLabel.getStyleClass().add("book-card-label");
            detailsBox.getChildren().addAll(titleLabel, authorLabel, genreLabel);
            
            resultBox.getChildren().addAll(cover, detailsBox);
            searchResultsContainer.getChildren().add(resultBox);
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
    protected void onApplicationsButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("applications.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) applicationsButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Подані заяви");
    }
    
    @FXML
    protected void onCatalogButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("catalog.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) catalogButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Каталог книг");
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
        Stage stage = (Stage) searchNavButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Профіль");
    }
}
