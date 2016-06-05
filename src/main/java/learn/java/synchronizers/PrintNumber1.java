package learn.java.synchronizers;

public class PrintNumber1 implements Runnable {

	private int numberToPrint;
	private MySynchronizer mySm;
	
	


	public PrintNumber1(int number, MySynchronizer mySm) {
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
