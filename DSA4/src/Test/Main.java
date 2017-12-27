package Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;
import sort.DMergeSort;
import sort.ExtMergeSort;
import sort.heapSort;
import inputStream.Fread.*;
import inputStream.Buffer.*;
import inputStream.Mapping.*;
import inputStream.SystemCall.*;

public class Main {
	final static Logger logger = Logger.getLogger(Main.class);
	static int MAX_STREAMS=1;
	static int MAX_INTS=1000000;
	static int MAX_N=1;
	static int B=30000000;
	static List<Integer> values = new ArrayList();
	static void clearDIR() {

		  try {
			Files.walk(Paths.get("data/out/"))
			    .filter(Files::isRegularFile)
			    .map(Path::toFile)
			    .forEach(File::delete);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static void createDataSet(int l) {
		for(int j=0;j<l;j++) {
			MapOut mo = new MapOut("data/set"+j+".data",500000);
	        for (int i = 0; i < MAX_INTS; i++) {
	        		int nr = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
	             mo.write(nr);
	        }    
	        mo.close();
		}
	}
	  public static void main(String[] args) {
		  int[] bufSizes = {100,10000,1000000};

		  createDataSet(MAX_STREAMS);
		  
		  BasicConfigurator.configure();

		  
		  //System call
		  System.out.println("Test System call");
		  for(int Ks=1;Ks<=MAX_STREAMS;Ks++) {
			  System.out.println(Ks+" streams");
		  for(int N=0;N<MAX_N;N++) {
			  ExecutorService ExServ = Executors.newFixedThreadPool(Ks);
			  for(int i=0;i<Ks;i++) {
				  Runnable st = new ExpRunSysCall(i);
				  ExServ.execute(st);
			  }
			  ExServ.shutdown();
			  while(!ExServ.isTerminated()) {}
			  System.out.println("SystemCall test finished. No:"+N);
			  clearDIR();
			  }
		  }
		  
		  
		//Fread/ fwrite
		  System.out.println("Test Fread/Fwrite");
		  for(int Ks=1;Ks<=MAX_STREAMS;Ks++) {
			  System.out.println(Ks+" streams");
		  for(int N=0;N<MAX_N;N++) {
			  ExecutorService ExServ = Executors.newFixedThreadPool(Ks);
			  for(int i=0;i<Ks;i++) {
				  Runnable st = new ExpRunFread(i);
				  ExServ.execute(st);
			  }
			  ExServ.shutdown();
			  while(!ExServ.isTerminated()) {}
			  System.out.println("Fread/Fwrite test finished. No:"+N);
			  
			  }
		  }
		  
		  //Buffer
		  System.out.println("Test Buffer");
		  for(int Ks=1;Ks<=MAX_STREAMS;Ks++) {
			  System.out.println(Ks+" streams");
			  for(int bf = 0;bf<bufSizes.length;bf++) {
				  System.out.println("Set buffer size: "+bufSizes[bf]);
		  for(int N=0;N<MAX_N;N++) {
			  ExecutorService ExServ = Executors.newFixedThreadPool(Ks);
			  for(int i=0;i<Ks;i++) {
				  Runnable st = new ExpRunBuffer(i,bufSizes[bf]);
				  ExServ.execute(st);
			  }
			  ExServ.shutdown();
			  while(!ExServ.isTerminated()) {}
			  System.out.println("Buffer test finished. No:"+N);
			  clearDIR();
			  }
		  }
		  }
		  //Mapping

		  System.out.println("Test Mapping");
		  for(int Ks=1;Ks<=MAX_STREAMS;Ks++) {
			  System.out.println(Ks+" streams");
			  for(int bf = 0;bf<bufSizes.length;bf++) {
				  System.out.println("Set buffer size: "+bufSizes[bf]);
		  for(int N=0;N<MAX_N;N++) {
			  ExecutorService ExServ = Executors.newFixedThreadPool(Ks);
			  for(int i=0;i<Ks;i++) {
				  Runnable st = new ExpRunMap(i,bufSizes[bf]);
				  ExServ.execute(st);
			  }
			  ExServ.shutdown();
			  while(!ExServ.isTerminated()) {}
			  System.out.println("Mapping test finished. No:"+N);
			  clearDIR();
			  }
			  }
		  }
		  //heap sort internal memory
		  List<Integer> inp = new ArrayList();
		  MapInp mi = new MapInp("data/set0.data",1000000);
		  int b=0;
		  while(!mi.endOfStream()) {
			  //b=;
				  	b=mi.readNext();
					  inp.add(b);
		  }
		  //internal sort for comparison

			StopWatch wa = new Log4JStopWatch("test-internal heap sort N="+MAX_INTS);
		  heapSort a = new heapSort();
		  a.sort(inp);
		  a.outputResult("data/a.out", 100000);
		  wa.stop();

		  //multiway-merge sort external memory
			
			//the best value from previous part
			int[] d = {100,1000,10000,100000,1000000};
			int[] N= {100,10000,100000,1000000,30000000,300000000};
		  //multiway-merge sort external memory
		  //M B D
			for(int j=0;j<N.length;j++) {
				MAX_INTS=N[j];
			createDataSet(1);
			for(int m=10;m<10000000;m*=10) {
			  //different memory sizes
			  for(int i=0;i<d.length;i++) {
				wa = new Log4JStopWatch("test-multiwayMerge N="+N[j]+" M="+m+" d="+d[i]);
				ExtMergeSort dm = new ExtMergeSort(m,B,d[i]);
				  dm.firstSort();
				  dm.sort();
				  wa.stop();
				  dm.clear();
			  }
			}
			File file = new File("data/set0.data");
			file.delete();
			}
	  }
	  public static class ExpRunSysCall implements Runnable {
			  int i;
			  ExpRunSysCall(int arg0) {
				  this.i=arg0;
			  }
 
			@Override
			public void run() {
				// TODO Auto-generated method stub
				StopWatch wa = new Log4JStopWatch("test-SystemCalls");
				SystemCallsInputStream inp = new SystemCallsInputStream();
				SystemCallsOutputStream out = new SystemCallsOutputStream();
				inp.open("data/set"+i+".data");
				out.create("data/out/outp"+i+".data");
				while(!inp.endOfStream()) {
					out.write(inp.readNext());
				}
				out.close();
				
				logger.info("Stream_"+i);
				wa.stop();
								
			}
		  }

	  public static class ExpRunFread implements Runnable {
			  int i;
			  ExpRunFread(int arg0) {
				  this.i=arg0;
			  }
 
			@Override
			public void run() {
				// TODO Auto-generated method stub
				StopWatch wa = new Log4JStopWatch("test-FreadFwrite");
				FreadStream inp = new FreadStream();
				FwriteStream out = new FwriteStream();
				inp.open("data/set"+i+".data");
				
				out.create("data/out/outp"+i+".data");
				
				while(!inp.endOfStream()) {
					out.write(inp.readNext());
				}
				out.close();
				logger.info("Stream_"+i);
				wa.stop();
								
			}
		  }

	  public static class ExpRunBuffer implements Runnable {
			  int i,B;
			  ExpRunBuffer(int arg0, int arg1) {
				  this.i=arg0;
				  this.B=arg1;
			  }
 
			@Override
			public void run() {

				StopWatch wa = new Log4JStopWatch("test-BufferOpenWrite");
				BufferedInputStream inp = new BufferedInputStream();
				BufferedOutputStream out = new BufferedOutputStream();
				inp.SetBufSize(B);
				out.SetBufSize(B);
				inp.open("data/set"+i+".data");
				out.create("data/out/outp"+i+".data");
				
				while(!inp.endOfStream()) {
					out.write(inp.readNext());
				}
				
				out.close();
				logger.info("Stream_"+i);
				wa.stop();
								
			}
		  }
	  

	  public static class ExpRunMap implements Runnable {
			  int i,B;
			  ExpRunMap(int arg0, int arg1) {
				  this.i=arg0;
				  this.B=arg1;
			  }
 
			@Override
			public void run() {			
				StopWatch wa = new Log4JStopWatch("test-Map");
				MapInp inp = new MapInp("data/set"+i+".data",B);
				MapOut out = new MapOut("data/out/set"+i+".data",B);
				out.open("data/out/set"+i+".data");
				while(!inp.endOfStream()) {
					out.write(inp.readNext());
				}
				
				out.close();
				logger.info("Stream_"+i);
				wa.stop();
			}
		  }

}
			  
		  /*List<List<Integer>> inp = new ArrayList<List<Integer>>();
		  MapInp mi = new MapInp("data/output.data",4);
		  int b=0;
		  while(!mi.eof()) {
			  List<Integer> t=new ArrayList<Integer>();
			  for(int i=0;i<mi.bufSize;i++) {
				  //
				  if(!mi.eof())
					  t.add(mi.readNextInt());
			  }
			  
			  inp.add(t);
		  }
		  for(int i=0;i<inp.size();i++) {
			  for(int j=0;j<inp.get(i).size();j++) {
				  //System.out.println(inp.get(i).get(j));
			  }
		  }
		  System.out.println("ab");
		  DMergeSort a = new DMergeSort();
		  a.kMerge(inp);*/
	/*	  MapOut mo = new MapOut("data/out.data");

        for (int i = 0; i < 1000000; i++) {
             Random intNr = new Random();
             int nr = intNr.nextInt(Integer.MAX_VALUE);
             mo.write(nr);
         }*/
        //MapInp mi = new MapInp("data/out.data");
		  
