package learn.java.synchronizers;


public class MySynchronizerOldWay implements MySynchronizer {
	private int[] printSlot = new int[3];

	/* (non-Javadoc)
	 * @see learn.java.synchronizers.MySynchronizerI#controlPrinting(int)
	 */
	public synchronized void controlPrinting(int numberToPrint){
		while(printSlot[numberToPrint-1] != 0)
			try {
				wait();
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
			notifyAll();
		}
	}
	
	private void reset(){
		printSlot[0]=0;
		printSlot[1]=0;
		printSlot[2]=0;
	}
	
}	
