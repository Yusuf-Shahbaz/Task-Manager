import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
/**
 * TaskFileHandler provides functionality for reading and writing tasks to a file.
 * It supports both text-based file I/O as well as object serialization for faster and more reliable storage.
 */
public class TaskFileHandler {
 
    /**
     * Writes a list of tasks to a file in CSV format.
     *
     * @param tasks the list of tasks to write to the file
     * @param fileName the name of the file to write to
     * @throws IOException if an I/O error occurs during file writing
     */
    public static void writeTasksToFile(List<Task> tasks, String fileName) throws IOException {
        validateFileName(fileName); // Validate the filename
 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Task task : tasks) {
                writer.write(formatTaskAsCSV(task)); // Write each task in CSV format
                writer.newLine();
            }
        }
    }
 
    /**
     * Reads a list of tasks from a CSV file.
     *
     * @param fileName the name of the file to read from
     * @return a list of tasks read from the file
     * @throws IOException if an I/O error occurs during file reading
     */
    public static List<Task> readTasksFromFile(String fileName) throws IOException {
        validateFileName(fileName); // Validate the filename
 
        List<Task> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Optional<Task> task = parseTaskFromCSV(line); // Parse each line as a task
                task.ifPresent(tasks::add); // Add valid tasks to the list
            }
        }
        return tasks;
    }
 
    /**
     * Serializes a list of tasks to a binary file for efficient storage.
     *
     * @param tasks the list of tasks to serialize
     * @param fileName the name of the file to write to
     * @throws IOException if an I/O error occurs during serialization
     */
    public static void serializeTasks(List<Task> tasks, String fileName) throws IOException {
        validateFileName(fileName); // Validate the filename
 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(tasks);
        }
    }
 
    /**
     * Deserializes a list of tasks from a binary file.
     *
     * @param fileName the name of the file to read from
     * @return a list of tasks read from the binary file
     * @throws IOException if an I/O error occurs during deserialization
     * @throws ClassNotFoundException if the class of the serialized object cannot be found
     */
    @SuppressWarnings("unchecked")
    public static List<Task> deserializeTasks(String fileName) throws IOException, ClassNotFoundException {
        validateFileName(fileName); // Validate the filename
 
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<Task>) ois.readObject();
        }
    }
 
    /**
     * Validates the file name to ensure it is not null or empty.
     *
     * @param fileName the name of the file to validate
     * @throws IllegalArgumentException if the file name is null or empty
     */
    private static void validateFileName(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty.");
        }
    }
 
    /**
     * Checks if the specified file exists.
     *
     * @param fileName the name of the file to check
     * @return true if the file exists, false otherwise
     */
    public static boolean doesFileExist(String fileName) {
        validateFileName(fileName); // Validate the filename
        return Files.exists(Paths.get(fileName));
    }
 
    /**
     * Deletes the specified file from the system.
     *
     * @param fileName the name of the file to delete
     * @throws IOException if an I/O error occurs while deleting the file
     */
    public static void deleteFile(String fileName) throws IOException {
        validateFileName(fileName); // Validate the filename
 
        if (!Files.deleteIfExists(Paths.get(fileName))) {
            throw new IOException("Failed to delete file: " + fileName);
        }
    }
 
    /**
     * Formats a Task object as a CSV string.
     *
     * @param task the task to format
     * @return a CSV string representing the task
     */
    private static String formatTaskAsCSV(Task task) {
        return String.join(",",
                task.getName(),
                task.getDescription(),
                task.getDeadline().toString(),
                String.valueOf(task.isCompleted()));
    }
 
    /**
     * Parses a task from a CSV string.
     *
     * @param csv the CSV string representing the task
     * @return an Optional containing the parsed Task, or empty if parsing fails
     */
    private static Optional<Task> parseTaskFromCSV(String csv) {
        String[] taskDetails = csv.split(",");
        if (taskDetails.length != 4) {
            System.err.println("Invalid CSV format: " + csv);
            return Optional.empty(); // Return empty if the format is incorrect
        }
 
        Task task = new Task(taskDetails[0], taskDetails[1], taskDetails[2]);
        task.setCompleted(Boolean.parseBoolean(taskDetails[3]));
        return Optional.of(task);
    }
}
