package sample;

public class HRRNScheduler extends Scheduler{
    public double calcRR(int i, int currentTime){
        double responseRatio;
        int waitingTime;
        waitingTime = currentTime - queue.get(i).getArrivalTime();
        responseRatio = (double)(waitingTime + queue.get(i).getBurstTime())/queue.get(i).getBurstTime();
        return responseRatio;
    }
    public void changeProcess(int currentTime){
        Processor.setIdleTime(currentTime);
        if(!Processor.getID().equals("idle")) result.add(new Process(Processor.getID(), Processor.getAwakeTime(), Processor.getIdleTime()));
        // Replace the process if waiting queue is not empty
        if(!queue.isEmpty()) {
            int  mostRR= 0;
            // find process with the largest RR at this point in time
            for(int i = 1; i < queue.size(); i++){
                if(calcRR(mostRR, currentTime) < calcRR(i, currentTime)) mostRR = i;
            }
            Processor = queue.get(mostRR);
            Processor.setAwakeTime(currentTime);
            queue.remove(mostRR);
        }
        // Idle the processor if it is empty
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
                    // Add scheduling time for non-default IDLE states
                    if(Processor.getArrivalTime() != 0) schedulingTime++;
                    changeProcess(i);
                }
                else{
                    schedulingTime++;
                    continue;
                }
            }
            // Replace after the process ends
            if(Processor.getBurstTime() == Processor.getRunningTime()){
                Processor.setTurnaroundTime(i - Processor.getArrivalTime());
                changeProcess(i);
            }
            // Increase the current cumulative execution time of the process
            if(!Processor.getID().equals("idle")) Processor.increasRunningTime();
        }
        printResult();
    }
}