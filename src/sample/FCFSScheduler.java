public class FCFSScheduler extends Scheduler {
    public void changeProcess(int currentTime){
        Processor.setIdleTime(currentTime);
        if(!Processor.getID().equals("idle")) result.add(new Process(Processor.getID(), Processor.getAwakeTime(), Processor.getIdleTime()));
        if(!queue.isEmpty()) {
            Processor = queue.get(0);
            Processor.setAwakeTime(currentTime);
            queue.remove(0);
        }
        else this.setIdle(currentTime);
    }

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