package sample;
import sample.Scheduler;

public class FCFSScheduler extends Scheduler {
    public void run(){
        int schedulingTime = getSchedulingTime();
        for(int i = 0; i <= schedulingTime; i++){
            insertQueue(i);
            if(Processor.getID().equals("idle")){
                if(!queue.isEmpty()){
                    if(Processor.getArrivalTime() != 0) schedulingTime++;
                    changeProcess(i);
                }
                else{
                    schedulingTime++;
                    continue;
                }
            }
            if(Processor.getBurstTime() == Processor.getRunningTime()){
                Processor.setTurnaroundTime(i - Processor.getArrivalTime());
                changeProcess(i);
            }
            if(!Processor.getID().equals("idle")) Processor.increasRunningTime();
        }
        printResult();
    }
}