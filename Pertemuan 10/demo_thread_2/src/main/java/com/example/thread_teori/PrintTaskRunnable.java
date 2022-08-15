package com.example.thread_teori;

import java.util.Random;

public class PrintTaskRunnable implements Runnable{

    private final String taskName;
    private final int limit;
    private final static Random generator = new Random();
    private final ThreadCallback threadCallback;
    private int total;
    private int angka2;

    public PrintTaskRunnable(String taskName, int limit, int total, int angka2) {
        this.taskName = taskName;
        this.limit = limit;
        this.total = total;
        this.angka2 = angka2;
        threadCallback = new ThreadCallback() {
            @Override
            public void onComplete (String result){
                System.out.println(result);}
        };
    }

    @Override
    public void run(){
        System.out.println(taskName+" is counting");
        for(int i=0; i<limit; i++){
            System.out.print(total+"+"+angka2+"= ");
            total=total+angka2;
            System.out.println(total);
        }
        threadCallback.onComplete("Runnable Task is Finished!");
    }
}
