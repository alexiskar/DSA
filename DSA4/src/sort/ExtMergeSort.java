package sort;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import inputStream.Mapping.*;
import sort.DMergeSort;


public class ExtMergeSort {

	private int M=10;
	private int B=1;
	private int d=8;
	private int dS = d;
	private int numOfFilesNM;
	private int step=0;
	private int addStep=0;
	private PriorityQueue<MapInp> heap;
	public ExtMergeSort(int M, int B) {
		this.M=M; //set size of available memory
		this.B=B;
	}
	public void firstSort() {
		MapInp mi = new MapInp("data/set0.data",B);
		int numOfInts = (int)Math.ceil(((double)mi.getFS()/4.0));
		numOfFilesNM= (int)Math.ceil(((double)numOfInts/(double)M));
		int numOfBuffs = (int)Math.ceil(((double)M/(double)B));
		
			for(int i=0;i<numOfFilesNM;i++) {
				List<List<Integer>> inpl = new ArrayList<List<Integer>>();
				for(int j=0;j<numOfBuffs;j++) {
					List<Integer> t=new ArrayList<Integer>();
					for(int l=0;l<B;l++) {
						if(!mi.endOfStream())
							t.add(mi.readNext());
					}
					//System.out.println(t.get(0));
					if(t.size()>0) inpl.add(t);
				}
				  DMergeSort a = new DMergeSort();
				  a.kMerge(inpl);
				  a.outputResult("data/out/sort"+i+"_.data",B);
			}
			addStep=numOfFilesNM%d;
			sort();

	}
	public void sort() {
		String add = (step>0)?"_"+(step+1):"_";
		if(step==0) { step=numOfFilesNM/d;}
		System.out.println("add: "+add);
		int i=0;
		int FS=0;
		String lfs = null;
		for(i=0;i<step;i++) {
			heap = new PriorityQueue<MapInp>(numOfFilesNM, new extComparator());
			for(int j=0;j<d;j++) {
			try {
				if(Files.exists(Paths.get("data/out/sort"+(i*d+j)+add+".data"))) {
				int f=(int)(new MapInp("data/out/sort"+(i*d+j)+add+".data",B).getFS());
				if(f>0) {
				heap.add(new MapInp("data/out/sort"+(i*d+j)+add+".data",B));
				FS+=(new MapInp("data/out/sort"+(i*d+j)+add+".data",B).getFS());
				lfs="data/out/sort"+(i*d+j)+add+".data";
				System.out.println(lfs);
				}
				}
			}
			catch(NullPointerException e) {
				System.out.println("data/out/sort"+i+".data");
			}
			}
			//merge
			MapOut mo = new MapOut("data/out/sort"+i+"_"+step+".data",FS/4);
			for(int j=0;j<FS/4;j++) {
				if(heap.peek().endOfStream()) break;
			//while(!heap.peek().endOfStream()) {
				int t = heap.peek().readNext();
				//System.out.println("Step: "+step+" val: "+t);
				mo.write(t);
				}
			mo.close();
			FS=0;
			heap.clear();
		}
		for(i=0;i<1;i++) {
		for(int j=0;j<d;j++) {
			//remove old files
			try {
			    Files.delete(Paths.get("data/out/sort"+(i*d+j)+add+".data"));
			} catch (NoSuchFileException x) {
			} catch (DirectoryNotEmptyException x) {
			    
			} catch (IOException x) {
			    // File permission problems are caught here.
			    System.err.println(x);
			}
		}
		}
		if(step==1 && Files.exists(Paths.get("data/out/sort1_2.data"))) {
			//if there is a file on pre-finish step
			heap.add(new MapInp("data/out/sort1_2.data",B));
			heap.add(new MapInp("data/out/sort0_1.data",B));
			FS+=(new MapInp("data/out/sort1_2.data",B).getFS());
			FS+=(new MapInp("data/out/sort0_1.data",B).getFS());

			MapOut mo = new MapOut("data/out/sort1.data",FS/4);
			//for(int j=0;j<B*(d-step);j++) {
			while(!heap.peek().endOfStream()) {
				int t = heap.peek().readNext();
				System.out.println("Step: val: "+t);
				mo.write(t);
				}
			mo.close();

			try {
			    Files.delete(Paths.get("data/out/sort1_2.data"));
			    Files.delete(Paths.get("data/out/sort0_1.data"));
			} catch (NoSuchFileException x) {
			} catch (DirectoryNotEmptyException x) {
			    
			} catch (IOException x) {
			    // File permission problems are caught here.
			    System.err.println(x);
			}
			
		}
		
		if(step==1 && addStep>0) {
			//if there are files which were left on the first step
			heap.clear();
			FS=0;
		for(int j=0;j<addStep;j++) {
			heap = new PriorityQueue<MapInp>(numOfFilesNM/dS, new extComparator());
			try {
				heap.add(new MapInp("data/out/sort"+(addStep*dS+j)+"_.data",B));
				FS+=(new MapInp("data/out/sort"+(addStep*dS+j)+"_.data",B).getFS());
			}
			catch(NullPointerException e) {
				System.out.println("Error: data/out/sort"+i+"_.data");
			}
			
		}
		
		
		MapOut mo = new MapOut("data/out/sort0_0.data",FS/4);
		while(!heap.peek().endOfStream()) {
		int t = heap.peek().readNext();
		mo.write(t);
		}
		mo.close();
		
		heap.clear();
		FS=0;

		for(int j=0;j<addStep;j++) {
			heap = new PriorityQueue<MapInp>(numOfFilesNM/dS, new extComparator());
			try {

		    Files.delete(Paths.get("data/out/sort"+(addStep*dS+j)+"_.data"));
			}
		    catch (NoSuchFileException x) {
		} catch (DirectoryNotEmptyException x) {
		    
		} catch (IOException x) {
		    // File permission problems are caught here.
		    System.err.println(x);
		}
			}
		}
		
		
		if(Files.exists(Paths.get("data/out/sort0_0.data"))) {
//final merge
			heap = new PriorityQueue<MapInp>(numOfFilesNM/dS, new extComparator());
			try {
				heap.add(new MapInp("data/out/sort0_0.data",B));
				FS+=(new MapInp("data/out/sort0_0.data",B).getFS());
				
				
				
				if(Files.exists(Paths.get("data/out/sort1.data"))) {
				System.out.println("1");
				heap.add(new MapInp("data/out/sort1.data",B));
				FS+=(new MapInp("data/out/sort1.data",B).getFS());
				}
				else {
					System.out.println("2");
					heap.add(new MapInp("data/out/sort0_1.data",B));
					FS+=(new MapInp("data/out/sort0_1.data",B).getFS());
				}
			}
			catch(NullPointerException e) {
				System.out.println("Error: data/out/sort"+i+"_.data");
			}

		MapOut mo = new MapOut("data/out/res.data",FS/4);
		while(!heap.peek().endOfStream()) {
		int t = heap.peek().readNext();
		mo.write(t);  
		}
		mo.close();
		
		
		
		}
	
		d/=2;
		step--;
		if(step>0) sort();
	}
	
}
