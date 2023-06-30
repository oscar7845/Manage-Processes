import java.util.ArrayList;

public abstract class Scheduler {
    ArrayList<Process> pArr;
    ArrayList<Process> queue;
    ArrayList<Process> result;
    Process Processor;

    public Scheduler(){
        pArr = new ArrayList<Process>();
        queue = new ArrayList<Process>();
        result = new ArrayList<Process>();
        setIdle(0);
    }
    public void setIdle(int i){
        Processor = new Process("idle", i, 0);
        Processor.setAwakeTime(i);
    }
    public int getSchedulingTime() {
        int totalBT = 0;
        for (int i = 0; i < pArr.size(); i++) {
            totalBT += pArr.get(i).getBurstTime();
        }
        return totalBT;
    }
    public void insertProcess(Process pi){
        pArr.add(pi);
    }
    public void insertQueue(int currentTime){
        for(int i = 0; i < pArr.size(); i++){
            if(pArr.get(i).getArrivalTime() == currentTime) queue.add(pArr.get(i));
        }
    }

    public void printResult(){
        for(int i = 0; i < result.size(); i++){
            System.out.print("[" + result.get(i).getID() + ": " + result.get(i).getArrivalTime() + " - " + result.get(i).getBurstTime() + "] ");
        }
        System.out.println();
        for(int i = 0; i < pArr.size(); i++){
            System.out.println("[" + pArr.get(i).getID() + "] AT : " + pArr.get(i).getArrivalTime() + "  BT : " + pArr.get(i).getBurstTime() + "  WT : " + pArr.get(i).getWaitingTime() + "  TT : " + pArr.get(i).getTurnaroundTime() + "  NTT : " + pArr.get(i).getNTT());
        }
    }

    public abstract void changeProcess(int currentTime);

    public abstract void run();

    public static void main(String args[]){
        Scheduler a = new FCFSScheduler();
        a.insertProcess(new Process("1", 1, 4));
        a.insertProcess(new Process("2", 2, 4));
        a.insertProcess(new Process("3", 3, 4));
        a.run();
    }
}

