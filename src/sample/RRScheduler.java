public class RRScheduler extends Scheduler {
    private int delta;

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
            if(!queue.isEmpty()){
                Processor.setIdleTime(currentTime);
                if(!Processor.getID().equals("idle")) result.add(new Process(Processor.getID(), Processor.getAwakeTime(), Processor.getIdleTime()));
                queue.add(Processor);
            }
        }
        else{
            Processor.setIdleTime(currentTime);
            if(!Processor.getID().equals("idle")) result.add(new Process(Processor.getID(), Processor.getAwakeTime(), Processor.getIdleTime()));
            if(!queue.isEmpty()) {
                Processor = queue.get(0);
                Processor.setAwakeTime(currentTime);
                queue.remove(0);
            }
            else this.setIdle(currentTime);
        }

    }

    public void run(){
        int runOutTimer = 0;
        int schedulingTime = getSchedulingTime();

        for(int i = 0; i < schedulingTime + 1; i++){
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
            if(Processor.getBurstTime() == Processor.getRunningTime() || runOutTimer == this.getDelta()){
                Processor.setTurnaroundTime(i - Processor.getArrivalTime());
                changeProcess(i);
                runOutTimer = 0;
            }
            if(!Processor.getID().equals("idle")){
                Processor.increasRunningTime();
                runOutTimer++;
            }
        }
        printResult();
    }
}