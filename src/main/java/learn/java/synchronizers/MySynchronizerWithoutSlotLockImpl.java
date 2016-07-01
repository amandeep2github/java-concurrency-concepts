package learn.java.synchronizers;

import java.util.concurrent.locks.Lock;


public class MySynchronizerWithoutSlotLockImpl {
	Lock lock;
		
	public MySynchronizerWithoutSlotLockImpl(Lock lock) {
		this.lock = lock;
	}

	public void controlPrinting(PrintNumberSyncImpl2 pn){
		lock.lock();
		try {
			pn.getPrintCondition().await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print(pn.getNumberToPrint());
		pn.getPrintConditionNext().signal();		
		lock.unlock();
		
	}
	
	
	
	
	
}	
