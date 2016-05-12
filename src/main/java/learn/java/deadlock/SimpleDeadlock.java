package learn.java.deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleDeadlock {
	public static void main(String[] args) {
		ExecutorService execServ = Executors.newFixedThreadPool(2);
		Task a = new Task("A");
		Task b = new Task("B");
		a.setOther(b);
		b.setOther(a);
		execServ.execute(a);
		execServ.execute(b);
		execServ.shutdown();
	}
}

class Task implements Runnable {
	private String name;
	private Task other;
	
	
	public Task(String name) {
		super();
		this.name = name;
	}
	public synchronized void execute(){
		System.out.println("Executing task: "+name);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		other.otherMethod();;
		
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "This is task: "+name;
	}
	public void run() {
		System.out.println("I am thread: "+Thread.currentThread());
		execute();
	}
	public void setOther(Task other) {
		this.other = other;
	}
	public synchronized void otherMethod(){
		System.out.println("I am other method of: "+name);
	}
	
}
