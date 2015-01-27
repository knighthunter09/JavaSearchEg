package com.netapp.trng.threads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ListManager {
	
	List<String> names=new ArrayList<String>();
	
	ReadWriteLock rwLock=new ReentrantReadWriteLock();
	
	public void addNames(String[] newNames)
	{
		rwLock.writeLock().lock();
		try{
			names.addAll(Arrays.asList(newNames));
		}finally{
			rwLock.writeLock().unlock();
		}
	}
	
	public String[] getNames()
	{
		rwLock.readLock().lock();
		try{
			return (String[])names.toArray();
		}finally{
			rwLock.readLock().unlock();
		}
	}

}
