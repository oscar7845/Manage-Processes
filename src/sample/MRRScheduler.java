public class MRRScheduler extends Scheduler {
    private int delta;

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
        if(Processor.getBurstTime() - Processor.getRunningTime() > 0 && Processor.getBurstTime() - Processor.getRunningTime() < getMercy() && !Processor.getID().equals("idle")){
        }
        else{
            if(Processor.getArrivalTime() != Processor.getIdleTime()) result.add(new Process(Processor.getID(), Processor.getAwakeTime(), Processor.getIdleTime()));
            if(Processor.getBurstTime() > Processor.getRunningTime()) queue.add(Processor);
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