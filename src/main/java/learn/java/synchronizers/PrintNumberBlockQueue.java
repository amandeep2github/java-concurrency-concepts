package learn.java.synchronizers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;

public class PrintNumberBlockQueue implements Runnable {

	private int numberToPrint;
	private BlockingQueue<Integer> blockingQueue;
	public int getNumberToPrint() {
		return numberToPrint;
	}

	

	public PrintNumberBlockQueue(int numberToPrint, BlockingQueue<Integer> blockingQueue) {
		super();
		this.numberToPrint = numberToPrint;
		this.blockingQueue = blockingQueue;
	}



	public void run() {
		try {
			blockingQueue.put(numberToPrint);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	
	
	

}
