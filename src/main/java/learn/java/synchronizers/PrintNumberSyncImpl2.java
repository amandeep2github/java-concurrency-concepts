package learn.java.synchronizers;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;

public class PrintNumberSyncImpl2 implements Runnable {

	private int numberToPrint;
	private MySynchronizerWithoutSlotLockImpl mySm;
	private Condition printCondition;
	private Condition printConditionNext;
	private CyclicBarrier cb;
	public int getNumberToPrint() {
		return numberToPrint;
	}

	public PrintNumberSyncImpl2(int numberToPrint, MySynchronizerWithoutSlotLockImpl mySm, Condition printCondition,
			Condition printConditionNext, CyclicBarrier cb) {
		super();
		this.numberToPrint = numberToPrint;
		this.mySm = mySm;
		this.printCondition = printCondition;
		this.printConditionNext = printConditionNext;
		this.cb = cb;
	}

	public void run() {
		mySm.controlPrinting(this);
	}

	public Condition getPrintCondition() {
		return printCondition;
	}

	public Condition getPrintConditionNext() {
		return printConditionNext;
	}
	
	
	
	

}
