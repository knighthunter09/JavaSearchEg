package com.netapp.trng.threads;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileSearch {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		BufferedReader kb=new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter file name to search: ");
		String fileName=kb.readLine();
		long startTime=System.currentTimeMillis();
		File[] roots=File.listRoots();
		/*for(File f:roots)
		{
			System.out.println(f+""+f.canRead());
		}*/
		Queue<File> foldersToSearch=new LinkedList<File>();
		for(File root:roots)
		{
			if(root.canRead())
			{
				foldersToSearch.addAll(Arrays.asList(root.listFiles()));
			}
		}
		List<File> foundOccurances=new ArrayList<File>();
		//Delete 1
		ExecutorService threadPool = Executors.newFixedThreadPool(4);
        CountDownLatch countLatch = new CountDownLatch(4);
        
		SearchWorker worker1=new SearchWorker(fileName, foldersToSearch, foundOccurances, countLatch);
		SearchWorker worker2=new SearchWorker(fileName, foldersToSearch, foundOccurances, countLatch);
		SearchWorker worker3=new SearchWorker(fileName, foldersToSearch, foundOccurances, countLatch);
		SearchWorker worker4=new SearchWorker(fileName, foldersToSearch, foundOccurances, countLatch);
		//Delete 4
		threadPool.execute(worker1);
		threadPool.execute(worker2);
		threadPool.execute(worker3);
		threadPool.execute(worker4);
		threadPool.shutdown();
	    countLatch.await();
//		worker1.start();worker2.start();worker3.start();worker4.start();		
//		worker1.join();worker2.join();worker3.join();worker4.join();
		long endTime=System.currentTimeMillis();
		System.out.println("Search took "+(endTime-startTime)+" ms");
		System.out.println("Found "+foundOccurances.size()+" files matching "+fileName);
		for(File fileFound: foundOccurances)
		{
			System.out.println(fileFound.getAbsolutePath());
		}
	}

}
