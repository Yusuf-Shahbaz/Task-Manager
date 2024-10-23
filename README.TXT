------------------------------------------------------------------------
Task Manager Application
------------------------------------------------------------------------
 
   Overview
 
This project is a   Task Manager   system designed using   Java   in   BlueJ  . It simulates the management and scheduling of tasks. The main components of the project include task handling, scheduling, and file management. The classes and their roles are described below.
 
   Class Overview
 
1.   Task  
   - This is the core class representing an individual task. It stores information about the task such as its name, description, and due date.
 
2.   TaskManager  
   - The   TaskManager   class handles the creation, deletion, and management of tasks. It stores a collection of tasks and provides functionality to update or retrieve tasks.
 
3.   TaskController  
   - This class serves as a controller between the user interface and the   TaskManager  . It handles input and output from the user and passes it to the appropriate functions in the   TaskManager  .
 
4.   TaskScheduler  
   - The   TaskScheduler   class is responsible for scheduling tasks, possibly based on priority or deadlines. It interacts with the   TaskManager   to retrieve tasks that need to be scheduled.
 
5.   TaskFileHandler  
   - This class deals with reading from and writing to files. It allows the   TaskManager   to load and save tasks to an external file, ensuring data persistence between sessions.
 
6.   TaskException  
   - A custom exception class designed to handle errors specific to the task management system, such as invalid task entries or scheduling conflicts.
 
7.   Main  
   - The entry point of the program. This class contains the main method, which initializes the   TaskController   and starts the application.
 
   Setup Instructions
 
    Prerequisites
-   BlueJ   should be installed on your system.
 
    Steps to Run the Project:
1. Open   BlueJ  .
2. Import the project by opening the folder containing the source files.
3. Compile all the classes.
4. Create an instance of the   Main   class and run it to start the application.
 
   Features
-   Task Management:   Create, update, and delete tasks.
-   Task Scheduling:   Automatically schedule tasks based on their priority and deadlines.
-   File Handling:   Save tasks to a file and load them later.
-   Error Handling:   Custom exceptions for task-specific issues.
 
   Usage
- Users can add new tasks by providing details such as task name and due date.
- Tasks can be scheduled and viewed in a particular order, based on deadlines or priorities.
- The application can load tasks from a file upon startup, ensuring that previously created tasks are available.
 

