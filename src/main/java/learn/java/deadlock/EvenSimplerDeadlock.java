package learn.java.deadlock;

public class EvenSimplerDeadlock {
	private Object lock1 = new Object();
	private Object lock2 = new Object();

	public void methodA(){
		synchronized (lock1) {
			System.out.println(String.format("%s - %s", Thread.currentThread(),"I am in methodA"));
			methodB();
		}

	}

	public void methodB(){
		synchronized (lock2) {
			System.out.println(String.format("%s - %s", Thread.currentThread(),"I am in methodB"));
			methodC();
		}

	}

	public void methodC(){
		synchronized (lock1) {
			System.out.println(String.format("%s - %s", Thread.currentThread(),"I am in methodC"));
			
		}

	}
}
