package learn.java.synchronizers;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class PrintNumber implements Runnable {

	private int number;
	private int noOfTimes;
	private CountDownLatch cdl;
	private CyclicBarrier cb;
	
	
	


	public PrintNumber(int number, int noOfTimes, CountDownLatch cdl, CyclicBarrier cb) {
		super();
		this.number = number;
		this.noOfTimes = noOfTimes;
		this.cdl = cdl;
		this.cb = cb;
	}





	public void run() {
		for(int i=0;i++<noOfTimes;){
			try {
				cb.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.printf("%s ::%d\n",Thread.currentThread(), number);
		}
		cdl.countDown();
	}

}
