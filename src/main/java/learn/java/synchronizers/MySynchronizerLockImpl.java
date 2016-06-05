package learn.java.synchronizers;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MySynchronizerLockImpl implements MySynchronizer{
	private int[] printSlot = new int[3];
	Lock lock = new ReentrantLock();

	public void controlPrinting(int numberToPrint){
		while(printSlot[numberToPrint-1] != 0)
				lock.lock();

		printSlot[numberToPrint-1] = numberToPrint;
		boolean allSlotsFilled = true;
		for(int i: printSlot){
			if(i == 0){
				allSlotsFilled = false;
				break;
			}
		}
		if(allSlotsFilled){
			for(int i: printSlot){
				System.out.print(i);
			}
			reset();
			lock.unlock();
		}
	}
	
	private void reset(){
		printSlot[0]=0;
		printSlot[1]=0;
		printSlot[2]=0;
	}
	
}	
