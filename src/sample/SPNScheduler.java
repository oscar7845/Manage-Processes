package sample;

public class SPNScheduler extends Scheduler {
    public void run() {
        int schedulingTime = getSchedulingTime();
        for (int i = 0; i <= schedulingTime; i++) {
            insertQueue(i);
            // If the processor is in the IDLE state
            if (Processor.getID().equals("idle")) {
                // Remove the idle state
                if (!queue.isEmpty()) {
                    // Add scheduling time for non-default IDLE states
                    if (Processor.getArrivalTime() != 0) schedulingTime++;
                    changeProcess(i);
                }
                // stay idle
                else {
                    schedulingTime++;
                    continue;
                }
            }
            // Replace after process termination
            if (Processor.getBurstTime() == Processor.getRunningTime()) {
                Processor.setTurnaroundTime(i - Processor.getArrivalTime());
                changeProcess(i);
            }
            // Increase the current cumulative execution time of the process
            if (!Processor.getID().equals("idle")) Processor.increasRunningTime();
        }
        printResult();
    }

    public void changeProcess(int currentTime) {
        Processor.setIdleTime(currentTime);
        if(!Processor.getID().equals("idle"))
            result.add(new Process(Processor.getID(), Processor.getAwakeTime(), Processor.getIdleTime()));
        //Replace the process if the wait queue is not empty
        if (!queue.isEmpty()) {
            int i=0,k=0,min=Integer.MAX_VALUE;
            for(i=0;i<queue.size();i++){
                if(queue.get(i).getBurstTime()<min){
                    min=queue.get(i).getBurstTime();
                    k=i;
                }
            }
            Processor = queue.get(k);
            Processor.setAwakeTime(currentTime);
            queue.remove(k);
        }
        //Set the processor to the idle state if it is empty
        else this.setIdle(currentTime);
    }
}