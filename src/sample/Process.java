package sample;

public class Process {
    private String ID;
    private int arrivalTime; // time the process arrived
    private int burstTime; // tot execution time of the process
    private int runningTime; // cumulative time the process has been running to date
    private int turnaroundTime; // Process's complete turnaround time
    private int idleTime; // when process was idle due to external factors
    private int awakeTime; // when process woke up again

    public Process(String _ID, int _arrivalTime, int _burstTime){
        setID(_ID);
        setArrivalTime(_arrivalTime);
        setBurstTime(_burstTime);
        setRunningTime(0);
        setIdleTime(0);
        setTurnaroundTime(0);
    }

    public String getPID() { 
        return ID;
    }
    public int getInputTime(){
        return arrivalTime;
    }

    public String getID() {
        return ID;
    }
    public void setID(String _ID) {
        this.ID = _ID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(int _arrivalTime) {
        this.arrivalTime = _arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }
    public void setBurstTime(int _burstTime) {
        this.burstTime = _burstTime;
    }

    public int getRunningTime() {
        return runningTime;
    }
    public void setRunningTime(int _runningTime) {
        this.runningTime = _runningTime;
    }
    public void increasRunningTime(){
        this.runningTime++;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }
    public void setTurnaroundTime(int _turnaroundTime) {
        this.turnaroundTime = _turnaroundTime;
    }

    public int getIdleTime() {
        return idleTime;
    }
    public void setIdleTime(int idleTime) {
        this.idleTime = idleTime;
    }

    public int getAwakeTime() {
        return awakeTime;
    }
    public void setAwakeTime(int awakeTime) {
        this.awakeTime = awakeTime;
    }

    public int getWaitingTime(){
        return this.getTurnaroundTime() - this.getBurstTime();
    }
    public float getNTT(){
        return (float)getTurnaroundTime()/getBurstTime();
    }
}