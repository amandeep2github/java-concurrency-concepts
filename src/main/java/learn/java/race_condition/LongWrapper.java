package learn.java.race_condition;

public class LongWrapper {
	private long l = 0L;
	private Object key = new Object();

	public long getValue() {
		return l;
	}
	
	public void incrementWithoutSynchronization(){
		l = l + 1;
	}
	
	public void incrementWithSynchronization(){
		synchronized (key) {
			l = l + 1;
		}
	}
	
}
