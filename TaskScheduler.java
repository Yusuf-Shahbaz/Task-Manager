import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
 
/**
 * TaskScheduler handles scheduling and running tasks at a specified time or interval.
 * It provides functionality for both one-time tasks and recurring tasks with delay and period options.
 */
public class TaskScheduler {
 
    private final Timer timer; // Timer to manage task scheduling
 
    /**
     * Constructs a new TaskScheduler.
     * This initializes the internal Timer used for scheduling tasks.
     */
    public TaskScheduler() {
        this.timer = new Timer(true); // Daemon thread to ensure the JVM can exit if the timer is running
    }
 
    /**
     * Schedules a one-time task to run after a specified delay.
     *
     * @param task the task to be scheduled (as a Runnable)
     * @param delay the delay in milliseconds before the task is executed
     * @throws IllegalArgumentException if the task or delay is invalid
     */
    public void scheduleTask(Runnable task, long delay) {
        if (task == null || delay < 0) {
            throw new IllegalArgumentException("Task cannot be null and delay must be non-negative.");
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, delay);
    }
 
    /**
     * Schedules a recurring task to run repeatedly with a fixed delay between executions.
     *
     * @param task the task to be scheduled (as a Runnable)
     * @param delay the initial delay in milliseconds before the task starts
     * @param period the period in milliseconds between consecutive executions
     * @throws IllegalArgumentException if the task, delay, or period is invalid
     */
    public void scheduleRecurringTask(Runnable task, long delay, long period) {
        if (task == null || delay < 0 || period <= 0) {
            throw new IllegalArgumentException("Task cannot be null, and delay/period must be positive.");
        }
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, delay, period);
    }
 
    /**
     * Schedules a recurring task to run after an initial delay, then with a specified period.
     * Provides a more readable API using TimeUnit for defining delays and periods.
     *
     * @param task the task to be scheduled (as a Runnable)
     * @param delay the initial delay
     * @param period the interval period
     * @param timeUnit the time unit for delay and period (e.g., SECONDS, MINUTES)
     */
    public void scheduleRecurringTask(Runnable task, long delay, long period, TimeUnit timeUnit) {
        scheduleRecurringTask(task, timeUnit.toMillis(delay), timeUnit.toMillis(period));
    }
 
    /**
     * Stops the task scheduler and cancels all scheduled tasks.
     * Once canceled, no further tasks can be scheduled with this scheduler instance.
     */
    public void stopScheduler() {
        timer.cancel();
    }
 
    /**
     * Gracefully stops the task scheduler and cancels all tasks.
     * This method waits for all currently running tasks to finish.
     *
     * @param waitTime the amount of time to wait before forcing cancellation
     * @param timeUnit the time unit of the wait time (e.g., SECONDS, MINUTES)
     */
    public void stopSchedulerGracefully(long waitTime, TimeUnit timeUnit) {
        try {
            // Wait for the specified time for running tasks to complete
            timeUnit.sleep(waitTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            System.out.println("Scheduler was interrupted while waiting for tasks to finish.");
        } finally {
            timer.cancel(); // Stop all scheduled tasks
        }
    }
 
    /**
     * Utility method for scheduling a task at a specific time in the future (e.g., 5 seconds from now).
     * Converts human-readable time intervals to milliseconds and schedules the task.
     *
     * @param task the task to be scheduled
     * @param time the time interval (e.g., 5, 10)
     * @param timeUnit the unit of time (e.g., SECONDS, MINUTES)
     */
    public void scheduleTaskWithDelay(Runnable task, long time, TimeUnit timeUnit) {
        long delayInMillis = timeUnit.toMillis(time);
        scheduleTask(task, delayInMillis);
    }
 
    /**
     * Checks if the scheduler has any active tasks running.
     *
     * @return true if the scheduler has active tasks, false otherwise
     */
    public boolean hasActiveTasks() {
        // Timer class doesn't provide a direct way to check active tasks,
        // but this method can be overridden in a more advanced scheduler
        return false; // Timer by itself doesn't offer this capability
    }
 
    /**
     * Reschedules an existing task with a new delay or period.
     * This method cancels the existing task and creates a new schedule for the task.
     *
     * @param oldTask the existing task (Runnable)
     * @param newTask the new task to be scheduled
     * @param delay the new delay in milliseconds
     * @param period the new period for recurring tasks, or 0 for one-time tasks
     * @param timeUnit the time unit for delay and period
     */
    public void rescheduleTask(Runnable oldTask, Runnable newTask, long delay, long period, TimeUnit timeUnit) {
        stopScheduler(); // Cancel the old task(s)
        if (period > 0) {
            scheduleRecurringTask(newTask, delay, period, timeUnit);
        } else {
            scheduleTask(newTask, timeUnit.toMillis(delay));
        }
    }
}
