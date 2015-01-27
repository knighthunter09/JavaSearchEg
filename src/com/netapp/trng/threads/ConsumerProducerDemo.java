package com.netapp.trng.threads;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ConsumerProducerDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		DataBuffer buffer= new DataBuffer();//new DataBufferWithLocks();
		DataProducer producer=new DataProducer(buffer);
		DataConsumer consumer=new DataConsumer(buffer);
		//Delete1
		Executor threadPool = Executors.newFixedThreadPool(2);
		Thread producerThread=new Thread(producer, "producer1");
		Thread consumerThread=new Thread(consumer, "consumer1");
		
		//Delete2
		threadPool.execute(producerThread);
		threadPool.execute(consumerThread);
		
		System.out.println("Starting threads......");
		producerThread.start();
		consumerThread.start();
		
		

	}

}
