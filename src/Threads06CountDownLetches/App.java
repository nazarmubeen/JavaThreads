package Threads06CountDownLetches;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class Processor implements Runnable {
	private CountDownLatch latch;
	private String name;

	public Processor(CountDownLatch latch,String name) {
		this.latch = latch;
		this.name=name;
	}

	@Override
	public void run() {
		System.out.println("Started ."+name+" "+Date.from(Instant.now()).toString());
		System.out.println("current latch count at start "+latch.getCount());
		try {
			System.out.println(name+" working on task");
		//	Thread.sleep(300);
			//used to reduce countdown of latch by one
			latch.countDown();
			System.out.println("current latch count at end "+latch.getCount());
			//used main thread to wait for number of countdown latch
			//Causes the current thread to wait until the latch has counted down to
		     //* zero
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}

public class App {

	public static void main(String[] args) throws InterruptedException {
		//used to initialize countdown latch here 2
		CountDownLatch latch = new CountDownLatch(2);

		//start executerservice
		ExecutorService executerService = Executors.newFixedThreadPool(3);

		for (int i = 0; i < 10; i++) {
			//submit services
			System.out.println(" service number "+i);
			executerService.submit(new Processor(latch,"A "+i));
		}
		
		System.out.println("In Main thread:waiting for thread to complete");
	
		System.out.println("In Main thread:Completed all count down threads starting for rest");
		
	}

}
