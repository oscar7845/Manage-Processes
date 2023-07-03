package sample;

public class SRTNScheduler extends Scheduler{
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
            changeProcess(i);
            if (!Processor.getID().equals("idle")) Processor.increasRunningTime();
        }
        printResult();
    }

    public void changeProcess(int currentTime) {
        if (Processor.getBurstTime() == Processor.getRunningTime()) {
            Processor.setIdleTime(currentTime);
            if(!Processor.getID().equals("idle"))
                result.add(new Process(Processor.getID(), Processor.getAwakeTime(), Processor.getIdleTime()));
            Processor.setTurnaroundTime(currentTime - Processor.getArrivalTime());
            if(queue.isEmpty()){
                this.setIdle(currentTime);
            }
            else {
                Processor = queue.get(min_RestTime());
                Processor.setAwakeTime(currentTime);
                queue.remove(min_RestTime());
            }
        }
        else if(!queue.isEmpty()){
            int p_rest = Processor.getBurstTime()-Processor.getRunningTime();
            int q_rest = queue.get(min_RestTime()).getBurstTime()-queue.get(min_RestTime()).getRunningTime();
            if(q_rest<p_rest){
                Processor.setIdleTime(currentTime);
                if(!Processor.getID().equals("idle"))
                    result.add(new Process(Processor.getID(), Processor.getAwakeTime(), Processor.getIdleTime()));
                queue.add(Processor);
                Processor=queue.get(min_RestTime());
                Processor.setAwakeTime(currentTime);
                queue.remove(min_RestTime());

            }
        }
    }

    public int min_RestTime(){
        int i = 0, k = 0, min = Integer.MAX_VALUE;
        if (!queue.isEmpty()) {
            for (i = 0; i < queue.size(); i++) {
                if (queue.get(i).getBurstTime()-queue.get(i).getRunningTime()< min) {
                    min = queue.get(i).getBurstTime()-queue.get(i).getRunningTime();
                    k = i;
                }
            }
        }
        return k;
    }
}