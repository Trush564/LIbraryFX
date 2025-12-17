package org.example.libraryfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.libraryfx.model.UserSession;
import org.example.libraryfx.model.Book;
import org.example.libraryfx.service.BookService;

import java.io.IOException;
import java.util.List;

public class CatalogController {
    @FXML
    private Button catalogButton;
    
    @FXML
    private Button logoutButton;
    
    @FXML
    private Button searchActionButton;
    
    @FXML
    private TextField searchTextField;
    
    @FXML
    private Button historyButton;
    
    @FXML
    private Button searchButton;
    
    @FXML
    private Button reserveButton;
    
    @FXML
    private Button applicationsButton;
    
    @FXML
    private Button rateButton;
    
    @FXML
    private Button reviewsButton;
    
    @FXML
    private VBox recommendedBooksContainer;
    
    @FXML
    private VBox scientificBooksContainer;
    
    @FXML
    private VBox fantasyBooksContainer;
    
    @FXML
    private VBox top100BooksContainer;
    
    @FXML
    private ScrollPane scrollPane;
    
    @FXML
    private Label userRoleLabel;
    
    @FXML
    private Label userLoginLabel;
    
    @FXML
    private Label userEmailLabel;
    
    @FXML
    private void initialize() {
        highlightActiveButton(catalogButton);
        loadBooksByCategory();
        // Встановлюємо стиль для ScrollPane, щоб прибрати білий фон
        if (scrollPane != null) {
            scrollPane.setStyle("-fx-background-color: #F4E4BC; -fx-control-inner-background: #F4E4BC;");
        }
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
    
    private void loadBooksByCategory() {
        displayBooks(BookService.getBooksByGenre("Рекомендовані"), recommendedBooksContainer);
        displayBooks(BookService.getBooksByGenre("Наукова література"), scientificBooksContainer);
        displayBooks(BookService.getBooksByGenre("Фантастика"), fantasyBooksContainer);
        displayBooks(BookService.getBooksByGenre("ТОП-100"), top100BooksContainer);
    }
    
    private void displayBooks(List<Book> books, VBox container) {
        if (container == null) return;
        container.getChildren().clear();
        javafx.scene.layout.HBox hbox = new javafx.scene.layout.HBox(10);
        for (Book book : books) {
            javafx.scene.layout.VBox bookCard = createBookCard(book);
            hbox.getChildren().add(bookCard);
        }
        container.getChildren().add(hbox);
    }
    
    private javafx.scene.layout.VBox createBookCard(Book book) {
        javafx.scene.layout.VBox card = new javafx.scene.layout.VBox(5);
        card.setAlignment(javafx.geometry.Pos.CENTER);
        card.setPrefWidth(100);
        
        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView();
        try {
            // Завжди використовуємо book.jpg для всіх книг з resources/org/example/libraryfx/images
            String imagePath = "/org/example/libraryfx/images/book.jpg";
            javafx.scene.image.Image image = new javafx.scene.image.Image(
                    CatalogController.class.getResourceAsStream(imagePath));
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
    
    private void highlightActiveButton(Button button) {
        ButtonStyleUtils.setActiveButton(button, 
            historyButton, searchButton, catalogButton, reserveButton, 
            applicationsButton, rateButton, reviewsButton, logoutButton);
    }
    
    @FXML
    protected void onSearchActionButtonClick() {
        String searchText = searchTextField.getText();
        // Логіка пошуку
    }
    
    @FXML
    protected void onHistoryButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("history.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) catalogButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Історія книг");
    }
    
    @FXML
    protected void onSearchButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("search.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) catalogButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Пошук книги");
    }
    
    @FXML
    protected void onReserveButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reserve.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) catalogButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Забронювати");
    }
    
    @FXML
    protected void onApplicationsButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("applications.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) catalogButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Подані заяви");
    }
    
    @FXML
    protected void onRateButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("rate.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) catalogButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Оцінити книгу");
    }
    
    @FXML
    protected void onReviewsButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reviews.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) catalogButton.getScene().getWindow();
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
        Stage stage = (Stage) catalogButton.getScene().getWindow();
        SceneUtils.switchScene(stage, root, "Профіль");
    }
}
