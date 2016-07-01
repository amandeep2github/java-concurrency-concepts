package learn.java.race_condition;

public class ProducerConsumerResourceWithVolatile {
	private int buffer[];
	private volatile int count = 0;
	
	
	
	public ProducerConsumerResourceWithVolatile(int size) {
		buffer = new int[size];
	}

	public void produce(){
		buffer[count++] = 1;
		System.out.println(String.format("%s - produced %d", Thread.currentThread(), count));
	}
	
	public void consume(){
		buffer[--count] = 0;
		System.out.println(String.format("%s - consumed %d", Thread.currentThread(), count+1));
	}
	
	public boolean isFull(){
		return count == buffer.length;
	}
	
	public boolean isEmpty(){
		return count == 0;
	}

	public int getCount() {
		return count;
	}
	
	
}
