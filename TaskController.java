import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
 
import java.time.LocalDate;
import java.util.Optional;
 
/**
 * Controller for handling Task management UI interactions.
 * Manages user input from text fields and updates task list view.
 */
public class TaskController {
 
    @FXML
    private TextField taskNameField;
 
    @FXML
    private TextArea taskDescriptionArea;
 
    @FXML
    private DatePicker taskDeadlinePicker;
 
    @FXML
    private ListView<Task> taskListView;
 
    private TaskManager taskManager;
 
    /**
     * Constructor initializes the TaskManager.
     */
    public TaskController() {
        taskManager = new TaskManager();
    }
 
    /**
     * Called after the FXML is loaded. Sets up the task list.
     */
    @FXML
    public void initialize() {
        // Initialize the ListView with observableArrayList so it reflects updates.
        taskListView.setItems(javafx.collections.FXCollections.observableArrayList(taskManager.getTasks()));
    }
 
    /**
     * Adds a new task based on user input from the UI form fields.
     * Performs validation on task name and deadline.
     */
    @FXML
    public void addTask() {
        String name = taskNameField.getText();
        String description = taskDescriptionArea.getText();
        LocalDate deadline = taskDeadlinePicker.getValue(); // Use LocalDate instead of String
 
        // Validation: Task Name and Deadline are mandatory fields.
        if (name.isEmpty() || deadline == null) {
            showError("Task Name and Deadline are required!");
            return;
        }
 
        Task task = new Task(name, description, deadline.toString());
        taskManager.addTask(task);
 
        // Add task to the ListView and clear form.
        taskListView.getItems().add(task);
        clearForm();
    }
 
    /**
     * Removes the selected task from the task list.
     * Alerts user for confirmation before removing the task.
     */
    @FXML
    public void removeTask() {
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
 
        if (selectedTask != null) {
            Optional<ButtonType> result = showConfirmation("Are you sure you want to delete the task?");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                taskManager.removeTask(selectedTask);
                taskListView.getItems().remove(selectedTask);
            }
        } else {
            showError("No task selected for removal!");
        }
    }
 
    /**
     * Marks the selected task as completed and updates the ListView.
     */
    @FXML
    public void completeTask() {
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            if (!selectedTask.isCompleted()) {
                taskManager.completeTask(selectedTask);
                taskListView.refresh(); // Refreshes the view to show updated task state
            } else {
                showError("Task is already completed!");
            }
        } else {
            showError("No task selected for completion!");
        }
    }
 
    /**
     * Clears the input form fields.
     */
    private void clearForm() {
        taskNameField.clear();
        taskDescriptionArea.clear();
        taskDeadlinePicker.setValue(null);
    }
 
    /**
     * Shows an error dialog to the user with a provided message.
     *
     * @param message the error message to display
     */
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
 
    /**
     * Shows a confirmation dialog before deleting a task.
     *
     * @param message the confirmation message to display
     * @return an Optional containing the user's response
     */
    private Optional<ButtonType> showConfirmation(String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText(null);
        alert.setContentText(message);
 
        return alert.showAndWait();
    }
 
    /**
     * Displays a success message to the user.
     *
     * @param message the success message to display
     */
    private void showSuccess(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
