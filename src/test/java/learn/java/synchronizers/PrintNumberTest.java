package learn.java.synchronizers;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

public class PrintNumberTest {

	@Test
	@Ignore
	public void testWithoutSynchronizers() {
		ByteArrayOutputStream baos= new ByteArrayOutputStream(500);
		PrintStream ps = System.out;
		System.setOut(new PrintStream(baos));
		ExecutorService es = Executors.newFixedThreadPool(3);
		PrintNumber2 pn1 = new PrintNumber2(1);
		PrintNumber2 pn2 = new PrintNumber2(2);
		PrintNumber2 pn3 = new PrintNumber2(3);
		for(int i=0;i<5;i++){
			es.execute(pn1);
			es.execute(pn2);
			es.execute(pn3);
		}
		try {
			es.awaitTermination(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.setOut(ps);
		Assert.assertFalse("123123123123123".equals(baos.toString()));
	}
	
	@Test
	@Ignore
	public void testWithOldWayOfSynchronization() {
		ByteArrayOutputStream baos= new ByteArrayOutputStream(500);
		PrintStream ps = System.out;
		System.setOut(new PrintStream(baos));
		ExecutorService es = Executors.newFixedThreadPool(3);
		MySynchronizer mySm = new MySynchronizerWithSlotOldWay();
		PrintNumberSyncImpl pn1 = new PrintNumberSyncImpl(1, mySm);
		PrintNumberSyncImpl pn2 = new PrintNumberSyncImpl(2, mySm);
		PrintNumberSyncImpl pn3 = new PrintNumberSyncImpl(3, mySm);
		for(int i=0;i<5;i++){
			es.execute(pn1);
			es.execute(pn2);
			es.execute(pn3);
		}
		try {
			es.awaitTermination(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.setOut(ps);
		Assert.assertEquals("123123123123123", baos.toString());//
	}
	
	@Test
	@Ignore
	public void testWithSynchronizationUsingLockImpl() {
		ByteArrayOutputStream baos= new ByteArrayOutputStream(500);
		PrintStream ps = System.out;
		System.setOut(new PrintStream(baos));
		ExecutorService es = Executors.newFixedThreadPool(3);
		MySynchronizer mySm = new MySynchronizerWithSlotLockImpl();
		PrintNumberSyncImpl pn1 = new PrintNumberSyncImpl(1, mySm);
		PrintNumberSyncImpl pn2 = new PrintNumberSyncImpl(2, mySm);
		PrintNumberSyncImpl pn3 = new PrintNumberSyncImpl(3, mySm);
		for(int i=0;i<5;i++){
			es.execute(pn1);
			es.execute(pn2);
			es.execute(pn3);
		}
		try {
			es.awaitTermination(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.setOut(ps);
		Assert.assertEquals("123123123123123", baos.toString());//
	}
	
	@Test
	@Ignore
	public void testWithSynchronizationWithoutSlotUsingLockImpl() {
		ByteArrayOutputStream baos= new ByteArrayOutputStream(500);
		PrintStream ps = System.out;
		System.setOut(new PrintStream(baos));
		ExecutorService es = Executors.newFixedThreadPool(3);
		Lock lock = new ReentrantLock();
		MySynchronizerWithoutSlotLockImpl ms = new MySynchronizerWithoutSlotLockImpl(lock);
		Condition cond1 = lock.newCondition();
		Condition cond2 = lock.newCondition();
		Condition cond3 = lock.newCondition();
		CyclicBarrier cb = new CyclicBarrier(3, new Runnable() {
			
			@Override
			public void run() {
				lock.lock();
				cond1.signal();
				lock.unlock();
			}
		});
		PrintNumberSyncImpl2 pn1 = new PrintNumberSyncImpl2(1, ms, cond1, cond2, cb);
		PrintNumberSyncImpl2 pn2 = new PrintNumberSyncImpl2(2, ms, cond2, cond3, cb);
		PrintNumberSyncImpl2 pn3 = new PrintNumberSyncImpl2(3, ms, cond3, cond1, cb);
		
		
		for(int i=0;i<5;i++){
			es.execute(pn1);
			es.execute(pn2);
			es.execute(pn3);
		}
		try {
			es.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.setOut(ps);
		Assert.assertEquals("123123123123123", baos.toString());//
	}
	
	@Test
	@Ignore
	public void testWithSynchronizationWithoutSlotUsingBlockingQueueImpl() {
		ByteArrayOutputStream baos= new ByteArrayOutputStream(500);
		PrintStream ps = System.out;
		System.setOut(new PrintStream(baos));
		ExecutorService es = Executors.newFixedThreadPool(3);
		BlockingQueue<Integer> bq1 = new LinkedBlockingQueue<>();
		BlockingQueue<Integer> bq2 = new LinkedBlockingQueue<>();
		BlockingQueue<Integer> bq3 = new LinkedBlockingQueue<>();
		PrintNumberBlockQueue pn1 = new PrintNumberBlockQueue(1, bq1);
		PrintNumberBlockQueue pn2 = new PrintNumberBlockQueue(2, bq2);
		PrintNumberBlockQueue pn3 = new PrintNumberBlockQueue(3, bq3);
		
		for(int i=0;i<5;i++){
			es.execute(pn1);
			es.execute(pn2);
			es.execute(pn3);
		}
		for(int i=0;i<5;i++){
			try {
				System.out.print(bq1.take());
				System.out.print(bq2.take());
				System.out.print(bq3.take());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		try {
			es.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.setOut(ps);
		Assert.assertEquals("123123123123123", baos.toString());//
	}
	
	@Test
	public void testWithSynchronizationWithoutSlotUsingCondition() {
		//ByteArrayOutputStream baos= new ByteArrayOutputStream(500);
		//PrintStream ps = System.out;
		//System.setOut(new PrintStream(baos));
		ExecutorService es = Executors.newFixedThreadPool(3);
		CountDownLatch cdl = new CountDownLatch(3);
		CyclicBarrier cb = new CyclicBarrier(3);
		PrintNumber pn1 = new PrintNumber(1, 5,cdl,cb);
		PrintNumber pn2 = new PrintNumber(2, 5,cdl,cb);
		PrintNumber pn3 = new PrintNumber(3, 5,cdl,cb);
		
		es.execute(pn1);
		es.execute(pn2);
		es.execute(pn3);
		es.shutdown();
		try {
			cdl.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

}
