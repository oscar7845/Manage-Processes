package sample;

public class SPNScheduler extends Scheduler {
    public void run() {
        int schedulingTime = getSchedulingTime();
        for (int i = 0; i <= schedulingTime; i++) {
            insertQueue(i);
            if (Processor.getID().equals("idle")) {
                if (!queue.isEmpty()) {
                    if (Processor.getArrivalTime() != 0) schedulingTime++;
                    changeProcess(i);
                }
                else {
                    schedulingTime++;
                    continue;
                }
            }
            if (Processor.getBurstTime() == Processor.getRunningTime()) {
                Processor.setTurnaroundTime(i - Processor.getArrivalTime());
                changeProcess(i);
            }
            if (!Processor.getID().equals("idle")) Processor.increasRunningTime();
        }
        printResult();
    }

    public void changeProcess(int currentTime) {
        Processor.setIdleTime(currentTime);
        if(!Processor.getID().equals("idle"))
            result.add(new Process(Processor.getID(), Processor.getAwakeTime(), Processor.getIdleTime()));
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
        else this.setIdle(currentTime);
    }
}