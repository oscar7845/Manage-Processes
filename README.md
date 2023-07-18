OS Scheduler
This tool simulates and visualizes process scheduling algorithms in operating systems, making them easier to grasp.

## The Idea
Understanding how processes are executed can be tricky due to its abstract nature. This tool helps by visualizing various scheduling algorithms in action, demonstrating how the computer allocates CPU time amongst processes.

## How it works
Users input the processes (arrival and burst times) they wish to schedule. The tool then schedules these using one of the available algorithms: FCFS, RR, SPN, SRTN, HRRN, or MRR. It outputs a timeline showing process information such as waiting time, turnaround time, and normalized turnaround.

## How it was built
used Java's abstract classes and interfaces to set up the scheduling algorithms. The GUI is created with JavaFX, which offers many features for user-friendly interfaces.
The workspace.xml file contains all the necessary application components in a single jar file. It employs bookmarks and ChangeListManager for easy navigation and monitoring of file changes.

## Bugs and challenges
Initially, plsanned to have one visualization section but ended up dividing it into a graphical scheduling timeline and a table of process data. Had to manage potential concurrency issues due to the multithreaded nature of JavaFX applications.
Code snippet for using Platform.runLater() to ensure UI updates run on the JavaFX app thread:

sh
Copy code
Label label = new Label();

new Thread(() -> {
    String newTxt = "Updated";
    
    Platform.runLater(() -> {
        label.setText(newTxt);
    });
}).start();

## Things learned
Creating the scheduling algorithms has deepened our understanding of CPU scheduling and the trade-offs involved. 

## Roadmap
Considering adding more scheduling algorithms like Shortest Job First (SJF), Priority Scheduling, and Multilevel Queue Scheduling. Looking at making the application more interactive, allowing users to step through the scheduling process one time unit at a time for a deeper understanding of the algorithms.
