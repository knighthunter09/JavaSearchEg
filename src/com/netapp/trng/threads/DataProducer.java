package com.netapp.trng.threads;

public class DataProducer implements Runnable {

	private DataBuffer buffer;

	public DataProducer(DataBuffer buufer)
	{
		this.buffer=buufer;
	}
	
	@Override
	public void run() {
		
		for(int i=0;i<10;i++)
		{
			buffer.produceData(i);
			System.out.println(Thread.currentThread().getName()+" produced: "+i);
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}

	}

}
