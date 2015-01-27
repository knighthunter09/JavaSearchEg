package com.netapp.trng.threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DataBufferWithLocks extends DataBuffer {
	
	int data;
	boolean available=false;
	
	Lock lock=new ReentrantLock();
	Condition notEmpty=lock.newCondition();
	Condition notFull=lock.newCondition();
	
	@Override
	public int consumeData()
	{
		lock.lock();
		try
		{
			while(!available)
			{
				try {
					notEmpty.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			available=false;
			notFull.signal();
			return data;
		}finally
		{
			lock.unlock();
		}
		
	}
	
	@Override
	public void produceData(int data)
	{
		lock.lock();
		try
		{
			while(available)
			{
				try {
					notFull.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			this.data=data;
			available=true;
			notEmpty.signal();
			
		}finally
		{
			lock.unlock();
		}
		
	}
	

}
