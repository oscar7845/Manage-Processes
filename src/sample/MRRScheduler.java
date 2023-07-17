package sample;

public class MRRScheduler extends Scheduler {
    private int delta; // Max execution time

    public MRRScheduler(int _delta){
        setDelta(_delta);
    }

    public int getDelta() {
        return delta;
    }
    public void setDelta(int delta) {
        this.delta = delta;
    }

    public static double baseLog(double x, double base) {
        return Math.log10(x) / Math.log10(base);
    }
    public int getMercy(){
        return (int)Math.floor(baseLog(delta, 2));
    }

    public void changeProcess(int currentTime){
        Processor.setIdleTime(currentTime);
        if(Processor.getBurstTime() - Processor.getRunningTime() > 0 && Processor.getBurstTime() - Processor.getRunningTime() <= getMercy() && !Processor.getID().equals("idle")){
        }
        else{
            if(!Processor.getID().equals("idle")) result.add(new Process(Processor.getID(), Processor.getAwakeTime(), Processor.getIdleTime()));
            // Requeue process when it's done
            if(Processor.getBurstTime() > Processor.getRunningTime()) queue.add(Processor);
            //Replace process if the wait queue is not empty
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