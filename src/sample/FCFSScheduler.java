package sample;

public class FCFSScheduler extends Scheduler {
    public void changeProcess(int currentTime){
        Processor.setIdleTime(currentTime);
        if(!Processor.getID().equals("idle")) result.add(new Process(Processor.getID(), Processor.getAwakeTime(), Processor.getIdleTime()));
        // Replace the process if waiting queue is not empty
        if(!queue.isEmpty()) {
            Processor = queue.get(0);
            Processor.setAwakeTime(currentTime);
            queue.remove(0);
        }
        // Set processor in idle state if it is empty
        else this.setIdle(currentTime);
    }

    public void run(){
        int schedulingTime = getSchedulingTime();
        for(int i = 0; i <= schedulingTime; i++){
            insertQueue(i);
            // If the processor is in an IDLE state
            if(Processor.getID().equals("idle")){
                // Release the IDLE state
                if(!queue.isEmpty()){
                    // Add scheduling time for non-default IDLE state
                    if(Processor.getArrivalTime() != 0) schedulingTime++;
                    changeProcess(i);
                }
                else{
                    schedulingTime++;
                    continue;
                }
            }
            // Replace after process termination
            if(Processor.getBurstTime() == Processor.getRunningTime()){
                Processor.setTurnaroundTime(i - Processor.getArrivalTime());
                changeProcess(i);
            }
            // Increase current cumulative execution time of a process
            if(!Processor.getID().equals("idle")) Processor.increasRunningTime();
        }
        printResult();
    }
}