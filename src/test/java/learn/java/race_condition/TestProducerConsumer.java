package learn.java.race_condition;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;

public class TestProducerConsumer {

	@Test
	@Ignore
	public void testWithoutVisibility() {
		ProducerConsumerResource pcr = new ProducerConsumerResource(10);

		Runnable producerTask = () -> {
			for (int i = 0; i < 50; i++) {
				while (pcr.isFull())
					;
				pcr.produce();
			}
			System.out.println("done producing");
		};
		Runnable consumerTask = () -> {
			for (int i = 0; i < 50; i++) {
				while (pcr.isEmpty())
					;
				pcr.consume();
			}
			System.out.println("done consuming");
		};

		ExecutorService es = Executors.newFixedThreadPool(2);
		es.execute(producerTask);
		es.execute(consumerTask);
		es.shutdown();
		try {
			es.awaitTermination(5, TimeUnit.SECONDS);
			// es.shutdownNow();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(0, pcr.getCount());

	}

	@Test @Ignore
	public void testWithVisibility() {
		ProducerConsumerResourceWithVolatile pcr = new ProducerConsumerResourceWithVolatile(10);

		Runnable producerTask = () -> {
			for (int i = 0; i < 50; i++) {
				while (pcr.isFull())
					;
				pcr.produce();
			}
			System.out.println("done producing");
		};
		Runnable consumerTask = () -> {
			for (int i = 0; i < 50; i++) {
				while (pcr.isEmpty())
					;
				pcr.consume();
			}
			System.out.println("done consuming");
		};

		ExecutorService es = Executors.newFixedThreadPool(2);
		es.execute(producerTask);
		es.execute(consumerTask);
		es.shutdown();
		try {
			es.awaitTermination(5, TimeUnit.SECONDS);
			// es.shutdownNow();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(0, pcr.getCount());

	}
	
	@Test
	public void testWithSynchronisation() {
		ProducerConsumerResourceWithSynchronize pcr = new ProducerConsumerResourceWithSynchronize(10);

		Runnable producerTask = () -> {
			for (int i = 0; i < 50; i++) {
				while (pcr.isFull())
					;
				pcr.produce();
			}
			System.out.println("done producing");
		};
		Runnable consumerTask = () -> {
			for (int i = 0; i < 50; i++) {
				while (pcr.isEmpty())
					;
				pcr.consume();
			}
			System.out.println("done consuming");
		};

		ExecutorService es = Executors.newFixedThreadPool(2);
		es.execute(producerTask);
		es.execute(consumerTask);
		es.shutdown();
		try {
			es.awaitTermination(5, TimeUnit.SECONDS);
			// es.shutdownNow();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(0, pcr.getCount());

	}

}
