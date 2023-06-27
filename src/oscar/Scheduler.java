package src.oscar;

import java.util.ArrayList;

public abstract class Scheduler {
    ArrayList<Process> pArr; 
    ArrayList<Process> queue;
    ArrayList<Process> result; 
    Process runningProcess;

    public Scheduler(){
        pArr = new ArrayList<Process>();
        queue = new ArrayList<Process>();
        result = new ArrayList<Process>();
        setIdle(); 
    }

    public void setIdle(){
        runningProcess = new Process("idle", 0, 99);
    } 

    public int getSchedulingTime(){
        int totalBT = 0;
        int firstATT = pArr.get(0).getArrivalTime();
        for(int i = 0;  i< pArr.size(); i++){
            totalBT += pArr.get(i).getBurstTime();
            if(firstATT > pArr.get(i).getArrivalTime()) firstATT = pArr.get(i).getArrivalTime();
        }
        return totalBT + firstATT;
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
            System.out.print("[" + result.get(i).getID() + ": " + result.get(i).getAwakeTime() + " - " + result.get(i).getIdleTime() + "] ");
        }
        System.out.println();
        for(int i = 0; i < pArr.size(); i++){
            System.out.println("[" + pArr.get(i).getID() + "] AT : " + pArr.get(i).getArrivalTime() + "  BT : " + pArr.get(i).getBurstTime() + "  WT : " + pArr.get(i).getWaitingTime() + "  TT : " + pArr.get(i).getTurnaroundTime() + "  NTT : " + pArr.get(i).getNTT());
        }
    }

    public void changeProcess(int currentTime){
        runningProcess.setIdleTime(currentTime);
        result.add(new Process(runningProcess.getID(), runningProcess.getArrivalTime(), runningProcess.getBurstTime(), runningProcess.getIdleTime(), runningProcess.getAwakeTime()));
        if(!queue.isEmpty()) {
            runningProcess = queue.get(0);
            runningProcess.setAwakeTime(currentTime);
            queue.remove(0);
        }
        else this.setIdle();
    }
    public abstract void run();

    public static void main(String args[]){
        Scheduler a = new RRScheduler(2);
        a.insertProcess(new Process("1", 0, 3));
        a.insertProcess(new Process("2", 1, 7));
        a.insertProcess(new Process("3", 3, 2));
        a.insertProcess(new Process("4", 5, 5));
        a.insertProcess(new Process("5", 6, 3));

        a.run();
    }
}

