package Threads08WaitAndNotify;

import java.util.ArrayList;
import java.util.logging.Logger;

public class ChangeLogicTest {

    public static void main(String[] args) {

    /*
        steps:

        1) create threads to print logics.
        2) create thread to send logic
     */


        Logic l = new Logic();

        Thread print = l.getPrintThread();
        Thread send = l.getSendThread();


        send.start();
        print.start();

        try {
            print.join();
            send.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}

class Logic {

    ArrayList<String> list = new ArrayList<>();

    Thread print;
    Thread send;


    Object lock = new Object();
    boolean acquired = false;



    Logic(){
         print = new Thread(new Runnable() {
            @Override
            public void run() {

                synchronized (lock) {

                    System.out.println("entered to print");
                    try {
                        System.out.println(" acquired " + acquired+" "+Thread.currentThread().getName());
                        acquired = true;
                        lock.wait();
                        System.out.println(" printing after waiting " + list);
                        acquired = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    lock.notifyAll();;
                }

            }
        }, "print");

         send = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(" send thread");

                while (acquired != true) {
                synchronized (lock) {

                    System.out.println(" acquired " + acquired+" "+Thread.currentThread().getName());

                        System.out.println("entered to send");
                        list.add("logic added");
                        lock.notify();
                    }
                }
            }
        }, "send");
    }



    public Thread getPrintThread() {

        return print;
    }

    public Thread getSendThread() {

        return send;
    }


}
