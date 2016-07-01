package learn.java.race_condition;

public class ProducerConsumerResourceWithSynchronize {
	private int buffer[];
	private volatile int count = 0;
	
	
	
	public ProducerConsumerResourceWithSynchronize(int size) {
		buffer = new int[size];
	}

	public synchronized void produce(){
		buffer[count++] = 1;
		System.out.println(String.format("%s - produced %d", Thread.currentThread(), count));
	}
	
	public synchronized void consume(){
		buffer[--count] = 0;
		System.out.println(String.format("%s - consumed %d", Thread.currentThread(), count+1));
	}
	
	public synchronized boolean isFull(){
		return count == buffer.length;
	}
	
	public synchronized boolean isEmpty(){
		return count == 0;
	}

	public int getCount() {
		return count;
	}
	
	
}
