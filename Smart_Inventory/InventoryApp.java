import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InventoryApp extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Smart Inventory Management - JavaFX");

        // 1. Δημιουργία του Πίνακα
        TableView<Product> table = new TableView<>();

        // 2. Δημιουργία Στηλών
        TableColumn<Product, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID")); // Πρέπει να ταιριάζει με το getID()

        TableColumn<Product, String> nameCol = new TableColumn<>("Όνομα");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Integer> qtyCol = new TableColumn<>("Ποσότητα");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Product, Double> priceCol = new TableColumn<>("Τιμή");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.getColumns().addAll(idCol, nameCol, qtyCol, priceCol);

        // 3. Φόρτωση Δεδομένων από το Repository σου
        ProductRepository repo = new ProductRepository();
        ObservableList<Product> data = FXCollections.observableArrayList(repo.getAllProducts());
        table.setItems(data);

        // 4. Layout
        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox, 600, 400);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}