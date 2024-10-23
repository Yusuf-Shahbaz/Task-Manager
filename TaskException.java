import java.util.logging.Level; // Java logging library
import java.util.logging.Logger; // Java logging library
 
/**
 * Custom exception class for handling task-related errors.
 * Extends the Exception class and provides additional features for
 * logging and error management.
 */
public class TaskException extends Exception {
    
    // Logger for logging error details
    private static final Logger logger = Logger.getLogger(TaskException.class.getName());
    
    /**
     * Constructor to create a new TaskException with a custom error message.
     * @param message The error message associated with the exception.
     */
    public TaskException(String message) {
        super(message);
        logError(message);
    }
    
    /**
     * Constructor to create a new TaskException with a custom error message
     * and a cause.
     * @param message The error message associated with the exception.
     * @param cause The throwable cause of this exception.
     */
    public TaskException(String message, Throwable cause) {
        super(message, cause);
        logError(message);
    }
 
    /**
     * Logs the error message using the built-in logging framework.
     * @param message The message to log.
     */
    private void logError(String message) {
        logger.log(Level.SEVERE, message);
    }
    
    /**
     * Logs the stack trace of this exception.
     * This method can be used to provide more detailed error tracking.
     */
    public void logStackTrace() {
        logger.log(Level.SEVERE, "Exception occurred", this);
    }
 
    /**
     * Retrieves the detailed error message for this exception, 
     * including the cause if available.
     * @return The detailed message.
     */
    @Override
    public String getMessage() {
        String message = super.getMessage();
        Throwable cause = getCause();
        if (cause != null) {
            message += " | Cause: " + cause.getMessage();
        }
        return message;
    }
 
    /**
     * Suggests possible solutions based on the error message.
     * This can be customized based on various types of task-related errors.
     * @return A suggestion string for handling the exception.
     */
    public String getSuggestedSolution() {
        String message = super.getMessage().toLowerCase();
        if (message.contains("timeout")) {
            return "Consider checking network connectivity or increasing the timeout value.";
        } else if (message.contains("invalid input")) {
            return "Ensure that all input values are within acceptable ranges.";
        } else {
            return "Check the application logs for more details.";
        }
    }
 
    /**
     * Returns the string representation of the exception, 
     * including the class name and detailed message.
     * @return A string containing class name and error message.
     */
    @Override
    public String toString() {
        return this.getClass().getName() + ": " + this.getMessage();
    }
}
