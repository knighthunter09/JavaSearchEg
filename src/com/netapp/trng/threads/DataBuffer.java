package com.netapp.trng.threads;

public class DataBuffer {
	
	private int data;
	
	boolean available=false;
	
	public synchronized int consumeData()
	{
		while(!available)
		{
			try {
				this.wait();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		available=false;
		this.notifyAll();
		return data;
	}
	
	public synchronized void produceData(int data)
	{
		while(available)
		{
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.data=data;
		available=true;
		this.notifyAll();
	}

}
