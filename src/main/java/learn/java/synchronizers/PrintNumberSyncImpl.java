package learn.java.synchronizers;

public class PrintNumberSyncImpl implements Runnable {

	private int numberToPrint;
	private MySynchronizer mySm;
	
	public int getNumberToPrint() {
		return numberToPrint;
	}

	public void setNumberToPrint(int numberToPrint) {
		this.numberToPrint = numberToPrint;
	}

	public PrintNumberSyncImpl(int number, MySynchronizer mySm) {
		super();
		this.numberToPrint = number;
		this.mySm = mySm;
	}

	public void run() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mySm.controlPrinting(numberToPrint);
	}

}
