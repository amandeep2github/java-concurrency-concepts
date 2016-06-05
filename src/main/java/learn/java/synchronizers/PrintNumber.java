package learn.java.synchronizers;

public class PrintNumber implements Runnable {

	private int number;
	
	
	public PrintNumber(int number) {
		super();
		this.number = number;
	}


	public void run() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print(number);
	}

}
