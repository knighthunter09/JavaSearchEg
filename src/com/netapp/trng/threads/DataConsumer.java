package com.netapp.trng.threads;

public class DataConsumer implements Runnable {
	
	private DataBuffer buffer;

	public DataConsumer(DataBuffer buffer)
	{
		this.buffer=buffer;
	}

	@Override
	public void run() {
		
		for(int i=0;i<10;i++)
		{
			int data=buffer.consumeData();
			System.out.println(Thread.currentThread().getName()+" consumed: "+data);
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}

	}

}
