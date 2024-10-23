import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
 
/**
 * TaskManager is responsible for managing a list of tasks.
 * It provides methods to add, remove, complete, search, and filter tasks.
 * This class is thread-safe to ensure proper task management in concurrent environments.
 */
public class TaskManager {
 
    private final List<Task> tasks; // Synchronized list to ensure thread safety
 
    /**
     * Initializes a new TaskManager with an empty task list.
     */
    public TaskManager() {
        tasks = Collections.synchronizedList(new ArrayList<>());
    }
 
    /**
     * Adds a task to the task list.
     *
     * @param task the task to be added
     * @throws IllegalArgumentException if the task already exists
     */
    public void addTask(Task task) {
        if (tasks.contains(task)) {
            throw new IllegalArgumentException("Task already exists in the list.");
        }
        tasks.add(task);
    }
 
    /**
     * Removes a task from the task list.
     *
     * @param task the task to be removed
     * @throws IllegalArgumentException if the task does not exist
     */
    public void removeTask(Task task) {
        if (!tasks.contains(task)) {
            throw new IllegalArgumentException("Task not found in the list.");
        }
        tasks.remove(task);
    }
 
    /**
     * Marks the specified task as completed.
     *
     * @param task the task to mark as completed
     * @throws IllegalArgumentException if the task does not exist
     */
    public void completeTask(Task task) {
        if (!tasks.contains(task)) {
            throw new IllegalArgumentException("Task not found in the list.");
        }
        task.setCompleted(true);
    }
 
    /**
     * Returns the list of all tasks.
     * The list is synchronized to prevent concurrent modification.
     *
     * @return a synchronized list of tasks
     */
    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks); // Return unmodifiable view
    }
 
    /**
     * Clears all completed tasks from the task list.
     * Uses synchronized block to ensure thread safety.
     */
    public void clearCompletedTasks() {
        synchronized (tasks) {
            tasks.removeIf(Task::isCompleted);
        }
    }
 
    /**
     * Finds a task by its name.
     *
     * @param name the name of the task to search for
     * @return an Optional containing the found task, or empty if not found
     */
    public Optional<Task> findTaskByName(String name) {
        return tasks.stream()
                .filter(task -> task.getName().equalsIgnoreCase(name))
                .findFirst();
    }
 
    /**
     * Sorts tasks by deadline in ascending order.
     */
    public void sortTasksByDeadline() {
        tasks.sort(Comparator.comparing(Task::getDeadline));
    }
 
    /**
     * Filters tasks that are overdue and returns them in a list.
     *
     * @return a list of overdue tasks
     */
    public List<Task> getOverdueTasks() {
        List<Task> overdueTasks = new ArrayList<>();
        synchronized (tasks) {
            for (Task task : tasks) {
                if (task.isOverdue()) {
                    overdueTasks.add(task);
                }
            }
        }
        return overdueTasks;
    }
 
    /**
     * Filters tasks that are completed and returns them in a list.
     *
     * @return a list of completed tasks
     */
    public List<Task> getCompletedTasks() {
        List<Task> completedTasks = new ArrayList<>();
        synchronized (tasks) {
            for (Task task : tasks) {
                if (task.isCompleted()) {
                    completedTasks.add(task);
                }
            }
        }
        return completedTasks;
    }
 
    /**
     * Filters tasks that are pending (not completed) and returns them in a list.
     *
     * @return a list of pending tasks
     */
    public List<Task> getPendingTasks() {
        List<Task> pendingTasks = new ArrayList<>();
        synchronized (tasks) {
            for (Task task : tasks) {
                if (!task.isCompleted()) {
                    pendingTasks.add(task);
                }
            }
        }
        return pendingTasks;
    }
 
    /**
     * Returns the total count of tasks in the manager.
     *
     * @return the number of tasks
     */
    public int getTotalTaskCount() {
        return tasks.size();
    }
 
    /**
     * Returns the count of completed tasks.
     *
     * @return the number of completed tasks
     */
    public int getCompletedTaskCount() {
        return (int) tasks.stream().filter(Task::isCompleted).count();
    }
 
    /**
     * Returns the count of pending tasks.
     *
     * @return the number of pending tasks
     */
    public int getPendingTaskCount() {
        return (int) tasks.stream().filter(task -> !task.isCompleted()).count();
    }
 
    /**
     * Updates an existing task with new details.
     *
     * @param oldTask the task to be updated
     * @param newTask the new task details to replace the old one
     * @throws IllegalArgumentException if the old task does not exist
     */
    public void updateTask(Task oldTask, Task newTask) {
        int index = tasks.indexOf(oldTask);
        if (index == -1) {
            throw new IllegalArgumentException("Task not found.");
        }
        tasks.set(index, newTask);
    }
}
