
package org.example.libraryfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.example.libraryfx.model.Book;
import org.example.libraryfx.model.UserSession;
import org.example.libraryfx.service.BookService;

import java.io.IOException;

public class RateController {
    @FXML
    private Button rateButton;
    
    @FXML
    private Button logoutButton;
    
    @FXML
    private Button reviewsButton;
    
    @FXML
    private Button catalogButton;
    
    @FXML
    private VBox booksToRateContainer;
    
    @FXML
    private Label userRoleLabel;
    
    @FXML
    private Label userLoginLabel;
    
    @FXML
    private Label userEmailLabel;
    
    @FXML
    private void initialize() {
        highlightActiveButton(rateButton);
        loadBooksForRating();
        loadUserData();
    }
    
    private void highlightActiveButton(Button button) {
        // RateController має обмежений набір кнопок, тому використовуємо тільки ті, що є
        ButtonStyleUtils.setActiveButton(button, rateButton, catalogButton, reviewsButton, logoutButton);
    }
    
    /**
     * Завантажує всі книги та показує їх у вигляді карток з обкладинками,
     * щоб користувач міг візуально обрати книгу для оцінки.
     */
    private void loadBooksForRating() {
        if (booksToRateContainer == null) return;
        booksToRateContainer.getChildren().clear();
        
        var books = BookService.getAllBooks();
        javafx.scene.layout.HBox hbox = new javafx.scene.layout.HBox(10);
        
        for (Book book : books) {
            javafx.scene.layout.VBox card = createBookCard(book);
            hbox.getChildren().add(card);
        }
        
        booksToRateContainer.getChildren().add(hbox);
    }
    
    /**
     * Створює картку книги з обкладинкою та назвою (так само, як у каталозі).
     */
    private javafx.scene.layout.VBox createBookCard(Book book) {
        javafx.scene.layout.VBox card = new javafx.scene.layout.VBox(5);
        card.setAlignment(javafx.geometry.Pos.CENTER);
        card.setPrefWidth(100);
        
        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView();
        try {
            // Завжди використовуємо book.jpg для всіх книг з resources/org/example/libraryfx/images
            String imagePath = "/org/example/libraryfx/images/book.jpg";
            javafx.scene.image.Image image = new javafx.scene.image.Image(
                    RateController.class.getResourceAsStream(imagePath));
            imageView.setImage(image);
        } catch (Exception e) {
            System.err.println("Failed to load book image: " + e.getMessage());
        }
        imageView.setFitHeight(150);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        
        javafx.scene.control.Label titleLabel = new javafx.scene.control.Label(book.getTitle());
        titleLabel.setWrapText(true);
        titleLabel.getStyleClass().add("book-card-text");
        
        card.getChildren().addAll(imageView, titleLabel);
        return card;
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
    protected void onHistoryButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("history.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Історія книг");
    }
    
    @FXML
    protected void onSearchButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("search.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Пошук книги");
    }
    
    @FXML
    protected void onCatalogButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("catalog.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Каталог книг");
    }
    
    @FXML
    protected void onApplicationsButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("applications.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Подані заяви");
    }
    
    @FXML
    protected void onReserveButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reserve.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Забронювати");
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
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Профіль");
    }
}

