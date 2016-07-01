package learn.java.race_condition;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestRaceCondition {

	@Test
	public void testIncrementSingleThread() {
		LongWrapper lw = new LongWrapper();
		Runnable runnable = () -> {
			for(int i=0; i < 1_000; i++)
				lw.incrementWithoutSynchronization();

		};
		Thread t = new Thread(runnable);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(1000, lw.getValue());


	}

	@Test
	public void testIncrementMultiThreadNonSynchronized() {
		LongWrapper lw = new LongWrapper();
		Runnable runnable = () -> {
			for(int i=0; i < 1_000; i++)
				lw.incrementWithoutSynchronization();

		};
		Thread [] threads = new Thread[1_0000];
		for(int i=0; i<threads.length; i++){
			threads[i] = new Thread(runnable);
			threads[i].start();
			
		}
		for(int i=0; i<threads.length; i++){
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		assertNotEquals(10_000_000, lw.getValue());


	}
	
	@Test
	public void testIncrementMultiThreadSynchronized() {
		LongWrapper lw = new LongWrapper();
		Runnable runnable = () -> {
			for(int i=0; i < 1_000; i++)
				lw.incrementWithSynchronization();

		};
		Thread [] threads = new Thread[1_000];
		for(int i=0; i<threads.length; i++){
			threads[i] = new Thread(runnable);
			threads[i].start();
			
		}
		for(int i=0; i<threads.length; i++){
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		assertEquals(1000_000, lw.getValue());


	}

}
