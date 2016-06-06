package learn.java.synchronizers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MySynchronizerWithSlotLockImpl implements MySynchronizer{
	private int[] printSlot = new int[3];
	Lock lock = new ReentrantLock();
	Condition slotOccupied = lock.newCondition();

	public void controlPrinting(int numberToPrint){
		lock.lock();
		while(printSlot[numberToPrint-1] != 0)
			try {
				slotOccupied.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
			slotOccupied.signalAll();
		}
		lock.unlock();
	}
	
	private void reset(){
		printSlot[0]=0;
		printSlot[1]=0;
		printSlot[2]=0;
	}
	
}	
