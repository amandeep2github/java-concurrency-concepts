package learn.java.deadlock;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class TestDeadlock {

	@Test
	public void testEvenSimplerDeadlock() {
		EvenSimplerDeadlock esd = new EvenSimplerDeadlock();
		Runnable task1 = () -> esd.methodA();
		Runnable task2 = () -> esd.methodB();
		ExecutorService es = Executors.newFixedThreadPool(2);
		es.execute(task1);
		es.execute(task2);
		
	}

}
