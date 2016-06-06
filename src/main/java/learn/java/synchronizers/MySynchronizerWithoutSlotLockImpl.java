package learn.java.synchronizers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MySynchronizerWithoutSlotLockImpl implements MySynchronizer{
	Lock lock = new ReentrantLock();
	Condition[] condForPrint;
	Condition[] condNextWaiting;
		
	public MySynchronizerWithoutSlotLockImpl(int sequenceMax) {
		condForPrint = new Condition[sequenceMax];
		for(int i=0; i<sequenceMax;i++){
			condForPrint[i] = lock.newCondition();
			condNextWaiting[i] = lock.newCondition();
		}
	}

	public void controlPrinting(int numberToPrint){
		lock.lock();
		int signalling = numberToPrint%3;
		//if(numberToPrint != 1)
		condNextWaiting[signalling].signal();
		try {
			condForPrint[numberToPrint-1].await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print(numberToPrint);
		 
		
		try {
			condNextWaiting[signalling].await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		condForPrint[signalling].signal();		
		lock.unlock();
		
	}
	
	public void start(){
		lock.lock();
		condForPrint[0].signal();
		lock.unlock();
	}
	
	
	
}	
