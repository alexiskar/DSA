package Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
import inputStream.Fread.*;
import inputStream.Buffer.*;
import inputStream.Mapping.*;
import inputStream.SystemCall.*;

public class Main {
	final static Logger logger = Logger.getLogger(Main.class);
	static int MAX_STREAMS=10;
	static int MAX_INTS=30000;
	static int MAX_N=1;
	static List<Integer> values = new ArrayList();

	static void createDataSet(int l) {
		for(int j=0;j<l;j++) {
			MapOut mo = new MapOut("data/set"+j+".data",100000);
			//BufferedOutputStream mo = new BufferedOutputStream();
			//mo.create("data/set"+j+".data");
	        for (int i = 0; i < MAX_INTS; i++) {
	        		int nr = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
	             mo.write(nr);
	        }    
	        mo.close();
		}
	}
	  public static void main(String[] args) {
		  BasicConfigurator.configure();
		  createDataSet(MAX_STREAMS);
		  for(int Ks=1;Ks<=MAX_STREAMS;Ks++) {
			  System.out.println(Ks+" streams");
		  for(int N=0;N<MAX_N;N++) {
			  ExecutorService ExServ = Executors.newFixedThreadPool(Ks);
			  for(int i=0;i<Ks;i++) {
				  //Runnable st = new ExpRunSysCall(i);
				  Runnable st = new ExpRunFread(i);
				  ExServ.execute(st);
			  }
			  ExServ.shutdown();
			  while(!ExServ.isTerminated()) {}
			  System.out.println("SystemCall test finished. No:"+N);
			  }
		  }
		  
		  /*
		  List<List<Integer>> inp = new ArrayList<List<Integer>>();
		  MapInp mi = new MapInp("data/set1.data",15);
		  int b=0;
		  while(!mi.endOfStream()) {
			  List<Integer> t=new ArrayList<Integer>();
			  for(int i=0;i<mi.bufSize;i++) {
				  //
				  if(!mi.endOfStream())
					  t.add(mi.readNext());
			  }
			  
			  inp.add(t);
		  }
		  for(int i=0;i<inp.size();i++) {
			  Collections.sort(inp.get(i));
		  }
		  System.out.println("ab");
		  DMergeSort a = new DMergeSort("a",2);
		  //a.kMerge(inp);
		  */
	  }
	  public static class ExpRunSysCall implements Runnable {
			  int i;
			  ExpRunSysCall(int arg0) {
				  this.i=arg0;
			  }
 
			@Override
			public void run() {
				// TODO Auto-generated method stub
				SystemCallsInputStream inp = new SystemCallsInputStream();
				SystemCallsOutputStream out = new SystemCallsOutputStream();
				StopWatch wa = new Log4JStopWatch("test-SystemCalls");
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
				FreadStream inp = new FreadStream();
				FwriteStream out = new FwriteStream();
				StopWatch wa = new Log4JStopWatch("test-FreadFwrite");
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
		  
