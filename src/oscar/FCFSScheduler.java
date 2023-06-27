package src.oscar;

public class FCFSScheduler extends Scheduler {
    public void run(){
        int schedulingTime = getSchedulingTime(); 

        for(int i = 0; i < schedulingTime + 1; i++){
            insertQueue(i);
            if(runningProcess.getID().equals("idle") && !queue.isEmpty()){
                runningProcess = queue.get(0);
                queue.remove(0);
            }
            if(runningProcess.getBurstTime() == runningProcess.getRunningTime()){
                runningProcess.setTurnaroundTime(i - runningProcess.getArrivalTime());
                changeProcess(i);
            }
            if(!runningProcess.getID().equals("idle")){
                runningProcess.increasRunningTime();
            }
        }
        printResult();
    }
}