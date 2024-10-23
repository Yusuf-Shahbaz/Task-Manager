import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;
 
/**
 * Represents a task with a name, description, deadline, and completion status.
 * This class provides methods to manipulate task details and manage task states.
 */
public class Task {
 
    private String name;
    private String description;
    private LocalDate deadline; // Use LocalDate for better date management
    private boolean isCompleted;
 
    /**
     * Constructs a new Task with the given name, description, and deadline.
     *
     * @param name the name of the task
     * @param description a brief description of the task
     * @param deadline the deadline for the task in "YYYY-MM-DD" format
     * @throws IllegalArgumentException if the name or deadline is invalid
     */
    public Task(String name, String description, String deadline) {
        setName(name); // Validates the name
        setDescription(description); // Validates description (optional)
        setDeadline(deadline); // Validates the deadline date
        this.isCompleted = false; // Default to not completed
    }
 
    /**
     * Gets the task's name.
     *
     * @return the task's name
     */
    public String getName() {
        return name;
    }
 
    /**
     * Sets the task's name.
     *
     * @param name the new task name
     * @throws IllegalArgumentException if the name is null or empty
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Task name cannot be null or empty.");
        }
        this.name = name.trim();
    }
 
    /**
     * Gets the task's description.
     *
     * @return the task's description
     */
    public String getDescription() {
        return description;
    }
 
    /**
     * Sets the task's description. Optional field, can be empty.
     *
     * @param description the new task description
     */
    public void setDescription(String description) {
        if (description != null) {
            this.description = description.trim();
        } else {
            this.description = "";
        }
    }
 
    /**
     * Gets the task's deadline.
     *
     * @return the deadline as a LocalDate object
     */
    public LocalDate getDeadline() {
        return deadline;
    }
 
    /**
     * Sets the task's deadline. Validates the format to ensure it's a valid date.
     *
     * @param deadline the deadline in "YYYY-MM-DD" format
     * @throws IllegalArgumentException if the deadline format is invalid
     */
    public void setDeadline(String deadline) {
        try {
            this.deadline = LocalDate.parse(deadline);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected format is 'YYYY-MM-DD'.", e);
        }
    }
 
    /**
     * Checks if the task is completed.
     *
     * @return true if the task is completed, false otherwise
     */
    public boolean isCompleted() {
        return isCompleted;
    }
 
    /**
     * Marks the task as completed or not completed.
     *
     * @param isCompleted true to mark the task as completed, false to mark as not completed
     */
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
 
    /**
     * Returns the task's completion status as a string.
     *
     * @return "Completed" if the task is completed, "Pending" otherwise
     */
    public String getCompletionStatus() {
        return isCompleted ? "Completed" : "Pending";
    }
 
    /**
     * Returns the task's status as an easily readable string.
     *
     * @return a user-friendly string representation of the task
     */
    @Override
    public String toString() {
        return String.format(
            "Task{name='%s', description='%s', deadline='%s', status='%s'}",
            name, description, deadline, getCompletionStatus()
        );
    }
 
    /**
     * Compares this task to another task for equality.
     *
     * @param o the other task object
     * @return true if the tasks are equal in name and deadline, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(deadline, task.deadline);
    }
 
    /**
     * Generates a hash code for this task based on its name and deadline.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, deadline);
    }
 
    /**
     * Checks if the task's deadline is overdue based on the current date.
     *
     * @return true if the task is overdue, false otherwise
     */
    public boolean isOverdue() {
        return !isCompleted && LocalDate.now().isAfter(deadline);
    }
 
    /**
     * Extends the task's deadline by a specified number of days.
     *
     * @param days the number of days to extend the deadline
     * @throws IllegalArgumentException if the number of days is negative
     */
    public void extendDeadline(int days) {
        if (days < 0) {
            throw new IllegalArgumentException("Days to extend cannot be negative.");
        }
        this.deadline = this.deadline.plusDays(days);
    }
}
