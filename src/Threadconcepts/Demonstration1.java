package Threadconcepts;

class Demonstration1 {
    public static void main( String args[] ) throws InterruptedException {
        
        ExecuteMe executeMe = new ExecuteMe();
        Thread innerThread = new Thread(executeMe);
        //A daemon thread runs in the background but as soon as the main application thread exits, all daemon threads are killed by the JVM. A thread can be marked daemon as follows:
        innerThread.setDaemon(true);
        // Interrupt innerThread after waiting for 5 seconds
        System.out.println("Main thread sleeping at " + +System.currentTimeMillis() / 1000);
        Thread.sleep(30);
        innerThread.start();
        innerThread.interrupt();
        System.out.println("Main thread exiting at " + +System.currentTimeMillis() / 1000);
        //main thread wait for this thread to complete
        innerThread.join();

    }
}

class ExecuteMe implements Runnable {
 
  public void run() {
    while (true) {
      System.out.println("Say Hello over and over again.");
      try {
          //A thread can be made dormant for a specified period using the sleep method
        Thread.sleep(5);
      } catch (InterruptedException ie) {
        // swallow interrupted exception
          break;
      }
    }
  }
}