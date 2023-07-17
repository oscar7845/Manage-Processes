package sample;

public class RRScheduler extends Scheduler {
    private int delta; // Maximum execution time

    public RRScheduler(int _delta){
        setDelta(_delta);
    }

    public int getDelta() {
        return delta;
    }
    public void setDelta(int delta) {
        this.delta = delta;
    }

    public void changeProcess(int currentTime){
        if(Processor.getBurstTime() > Processor.getRunningTime()){
            // Requeue the process when it's done
            if(!queue.isEmpty()){
                Processor.setIdleTime(currentTime);
                if(!Processor.getID().equals("idle")) result.add(new Process(Processor.getID(), Processor.getAwakeTime(), Processor.getIdleTime()));
                queue.add(Processor);
                Processor = queue.get(0);
                Processor.setAwakeTime(currentTime);
                queue.remove(0);
            }
        }
        else{
            Processor.setIdleTime(currentTime);
            if(!Processor.getID().equals("idle")) result.add(new Process(Processor.getID(), Processor.getAwakeTime(), Processor.getIdleTime()));
            //Replace the process if the wait queue is not empty
            if(!queue.isEmpty()) {
                Processor = queue.get(0);
                Processor.setAwakeTime(currentTime);
                queue.remove(0);
            }
            //Set the processor to the idle state if it is empty
            else this.setIdle(currentTime);
        }

    }

    public void run(){
        int runOutTimer = 0;
        int schedulingTime = getSchedulingTime();

        for(int i = 0; i < schedulingTime + 1; i++){
            insertQueue(i);
            if(Processor.getID().equals("idle")){
                // Remove the idle state
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
            // Replace after process end or by runout timer
            if(Processor.getBurstTime() == Processor.getRunningTime() || runOutTimer == this.getDelta()){
                Processor.setTurnaroundTime(i - Processor.getArrivalTime());
                changeProcess(i);
                runOutTimer = 0;
            }
            // Increase the process' current cumulative execution time and runout timer
            if(!Processor.getID().equals("idle")){
                Processor.increasRunningTime();
                runOutTimer++;
            }
        }
        printResult();
    }
}
