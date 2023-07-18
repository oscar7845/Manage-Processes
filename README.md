# OS Scheduling Visualizer
This application simulates the process scheduling algorithms in operating systems, and it provides visual results for a better understanding. 
## The Idea
Scheduling governs the execution of processes and can be challenging to understnd since it is abstract. By visualizing how various scheduling algorithms operate in real time, I wanted to be able to get a clear understanding of how the algorithms distribute processor time among multiple processes.
## How it works
You can feed the application with the processes (arrival times, and burst times) you want to schedule. The processes are then scheduled using the algorithm you chose between FCFS, RR, SPN, SRTN, HRRN, and MRR. Then we can see a visual representation of the timeline. For each process we can see data for waiting time, turnaround time, and normalized turnaround.
## How it was built
Java abstract classes and interfaces define the general structure of the scheduling algorithms. JavaFX is used for the GUI and I chose it because of the many features we can use for creating interfaces that are pretty user-friendly. The data for processes and the scheduling results are stored in custom classes and ArrayLists are used to manage collections of objects.
The file workspace.xml file packs everything - the whole app and stuff it needs to run - into one easy to uuse jar file. It uses Bookmarks to jump to important bits of code and uses ChangeListManager to keep an eye on any changes made to files.
## Bugs and challenges
I started with one visualization section but ended up breaking down the output into two parts which are the graphical representation of the scheduling timeline and a tabular representation of the process data.
Also, an important part of the development was trying to mnage and avoid concurrency issues and this is due to the multi threaded nature of JavaFX application.
You could use Platform.runLater() so that code affecting the UI runs on the JavaFX app thread. Example:
```sh
Label label = new Label();

new Thread(() -> {
    String newTxt = "Updated";
    
    Platform.runLater(() -> {
        label.setText(newTxt);
    });
}).start();
```
## Things learned
Implementing the scheduling algorithms helps to understand and appreciate the trade offs involved in CPU scheduling and the efficient scheduling in overall system performance.
## Roadmap
What can be done is incorporate more scheduling algorithms like Shortest Job First (SJF), Priority Scheduling, and Multilevel Queue Scheduling. Add more interactivity to the application so that users can go through the scheduling process one unit of time at a time, which would provide an even better understanding of the algorithms.
