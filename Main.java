import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
 
/**
 * Entry point for the Task Manager JavaFX application.
 * Initializes the user interface and handles application lifecycle events.
 */
public class Main extends Application {
 
    /**
     * Starts the JavaFX application and loads the UI from the FXML file.
     * Also sets the application window title, size, and event handlers.
     *
     * @param primaryStage the main window of the application
     * @throws Exception if there is an issue loading the FXML file
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the main UI layout from the FXML file.
        Parent root = FXMLLoader.load(getClass().getResource("TaskManagerUI.fxml"));
 
        // Set the title for the primary stage.
        primaryStage.setTitle("Task Manager");
 
        // Load and apply an application icon (optional, replace with a valid image path).
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/task_manager_icon.png")));
 
        // Set the scene and define the initial window size.
        primaryStage.setScene(new Scene(root, 800, 600)); // Customize width and height as needed.
 
        // Add event handler for window close to handle unsaved changes or cleanup.
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                handleAppClose(event);
            }
        });
 
        // Show the window.
        primaryStage.show();
    }
 
    /**
     * Main method to launch the JavaFX application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args); // Launches the JavaFX application.
    }
 
    /**
     * Handles actions when the user attempts to close the application window.
     * You can prompt the user to save changes, confirm exit, or clean up resources.
     *
     * @param event the window event that triggered the close request
     */
    private void handleAppClose(WindowEvent event) {
        // You can prompt the user to save tasks or confirm exiting here.
        System.out.println("Application is closing. Perform cleanup tasks if needed.");
 
        // Example: Prompt user confirmation before closing (can be replaced with actual implementation).
        /*
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.CANCEL) {
            event.consume(); // Prevents the application from closing.
        }
        */
    }
}
