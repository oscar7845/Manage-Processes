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
        if(!queue.isEmpty()) {
            int  mostRR= 0;
            for(int i = 1; i < queue.size(); i++){
                if(calcRR(mostRR, currentTime) < calcRR(i, currentTime)) mostRR = i;
            }
            Processor = queue.get(mostRR);
            Processor.setAwakeTime(currentTime);
            queue.remove(mostRR);
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