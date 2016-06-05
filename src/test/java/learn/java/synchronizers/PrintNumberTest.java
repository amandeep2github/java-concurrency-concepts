package learn.java.synchronizers;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
		PrintNumber pn1 = new PrintNumber(1);
		PrintNumber pn2 = new PrintNumber(2);
		PrintNumber pn3 = new PrintNumber(3);
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
		MySynchronizer mySm = new MySynchronizerOldWay();
		PrintNumber1 pn1 = new PrintNumber1(1, mySm);
		PrintNumber1 pn2 = new PrintNumber1(2, mySm);
		PrintNumber1 pn3 = new PrintNumber1(3, mySm);
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
	public void testWithSynchronizationUsingLockImpl() {
		ByteArrayOutputStream baos= new ByteArrayOutputStream(500);
		PrintStream ps = System.out;
		System.setOut(new PrintStream(baos));
		ExecutorService es = Executors.newFixedThreadPool(3);
		MySynchronizer mySm = new MySynchronizerLockImpl();
		PrintNumber1 pn1 = new PrintNumber1(1, mySm);
		PrintNumber1 pn2 = new PrintNumber1(2, mySm);
		PrintNumber1 pn3 = new PrintNumber1(3, mySm);
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

}
