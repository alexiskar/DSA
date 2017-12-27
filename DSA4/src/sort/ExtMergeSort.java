package sort;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import inputStream.Mapping.*;
import sort.DMergeSort;


public class ExtMergeSort {
	private int MAX_STREAMS=10240;
	//for OS X http://krypted.com/mac-os-x/maximum-files-in-mac-os-x/
	private int M=10;
	private int B=1;
	private int d=4;
	private long step=0,stepF=0;
	private int nf=0;
	int numOfInts = 0;
	String add="";
	boolean interupted=false;
	public ExtMergeSort(int M, int B, int D) {
		//if(B>M) B=M;
		this.M=M; //set size of available memory
		this.B=B; //set buffer
		this.d=D; //set current d
	}
	public void firstSort() {
		MapInp mi = new MapInp("data/set0.data",B);
		numOfInts=(int)Math.ceil(((double)mi.getFS()/4.0));
		if(M>mi.getFS()/4) M = (int) (mi.getFS()/4);
		nf= (int)Math.ceil(((double)numOfInts/(double)M));
		step = stepF= (int) (Math.log(nf/d)/Math.log(d-1))+1;
		if(step<0) step=1;
		if(nf>MAX_STREAMS) {
			System.out.println("Too many files");
			interupted=true;
		}
		else {
		for(int j=0;j<nf;j++) {
			int i=0;
			List<Integer> listToSort = new ArrayList();
			for(i=0;i<M;i++) {
				if(!mi.endOfStream())
				listToSort.add(mi.readNext());
				else
					break;
			}
			Collections.sort(listToSort);
			MapOut mo=new MapOut("data/spl_"+j+".tmp",i); 
			for(i=0;i<listToSort.size();i++) {
				mo.write(listToSort.get(i));
			}
			mo.close();
			}
		if(nf==1) { File file = new File("data/spl_0.tmp");file.delete(); }
		}
		mi.close();
		}
			
	public void sort() {
		if(!interupted && nf>1) {
		int i=0;
		List<MapInp> lOS = new ArrayList();
		int lastNF=nf;
		if(step<stepF) add="_"+(step+1);
		for(i=0;i<nf;i++) {
			lOS.add(new MapInp("data/spl_"+i+add+".tmp",B));			
		}
		nf = (int)Math.ceil(((double)nf/(double)d));
		for(i=0;i<nf;i++) {
			int start=d*i;
			int end=start+d;
			if(i+1==nf) end = lOS.size();
			DMergeSort sortOut = new DMergeSort(lOS.subList(start, end),"data/spl_"+i+"_"+step+".tmp",B);			
		}
		for(MapInp file:lOS) file.close();
		step--;
		if(step>=0) sort();
		}
	}
	public void clear() {
		if(!interupted) {
		File file;
		file = new File("data/spl_0_0.tmp");
		file.delete();
		nf= (int)Math.ceil(((double)numOfInts/(double)M));
		for(long i=stepF+1;i>0;i--) {
			for(int j=0;j<nf;j++) {
				if(i==stepF+1) file = new File("data/spl_"+j+".tmp");
				else file = new File("data/spl_"+j+"_"+i+".tmp");
				file.delete();
			}
			nf = (int)Math.ceil(((double)nf/(double)d));
		}
	}
	}
	
}
