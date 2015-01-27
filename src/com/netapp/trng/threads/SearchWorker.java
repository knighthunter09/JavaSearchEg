package com.netapp.trng.threads;

import java.io.File;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

public class SearchWorker extends Thread {
	
	Queue<File> foldersToSearch;
	List<File> foundOccurances;
	String fileName;
	CountDownLatch latch;
	
	public SearchWorker(String fileName,Queue<File> foldersToSearch,List<File> foundOccurances)
	{
		this.fileName=fileName;
		this.foldersToSearch=foldersToSearch;
		this.foundOccurances=foundOccurances;
		this.latch = new CountDownLatch(5);
	}
	
	public SearchWorker(String fileName,Queue<File> foldersToSearch,List<File> foundOccurances, CountDownLatch latch)
	{
		this.latch = latch;
		this.fileName=fileName;
		this.foldersToSearch=foldersToSearch;
		this.foundOccurances=foundOccurances;
	}
	
	@Override
	public void run()
	{
			while(true)
			{
				File aFolder=foldersToSearch.poll();
				if(aFolder==null)
					break;
				searchInFolder(aFolder);
			}
			latch.countDown();
	}

	private void searchInFolder(File aFolder) {
		if(aFolder.isFile())
		{
			if(aFolder.getName().equalsIgnoreCase(fileName))
				foundOccurances.add(aFolder);
		}else if(aFolder.isDirectory() && aFolder.canRead())
		{
			//System.out.println(aFolder);
			File[] filesInFolder=aFolder.listFiles();
			if(filesInFolder!=null)
			{
				for(File file:filesInFolder)
				{
					searchInFolder(file);
				}
			}else
				System.out.println(aFolder);
		}
		
	}

}
