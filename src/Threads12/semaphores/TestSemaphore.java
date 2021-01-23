package Threads12.semaphores;

import java.util.concurrent.Semaphore;

public class TestSemaphore {

    public static void main(String[] args) {

        SemaTest t=new SemaTest();


        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<=5;i++){
                    System.out.println(" i acquire "+i);
                    t.acquire();
                }
            }
        });

        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<=5;i++){
                    try {
                        System.out.println(" i release "+i);
                        t.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }



}

class SemaTest{

    Semaphore semaphore=new Semaphore(2);

    void acquire(){
        System.out.println("available permits acquire " + semaphore.availablePermits());
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void release() throws InterruptedException {
        semaphore.release();
        System.out.println("available permits release" + semaphore.availablePermits());
    }


}