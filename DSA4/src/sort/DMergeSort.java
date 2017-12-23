package sort;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

//Written with inspiration by https://github.com/dpkagrawal/kwaymerge/blob/master/src/KWayMerging.java

public class DMergeSort {
	private PriorityQueue<BufSort> heap;
	private int strSize=1;
	//List<>
	public DMergeSort(String fn,int strSize) {
		this.strSize=strSize;
	}
	public void kMerge(List<List<Integer>> inp) {
		List <Integer> out = new ArrayList<Integer>();
		heap = new PriorityQueue<BufSort>(inp.size(), new BufSortComparator());
		int[] index = new int[inp.size()];
		
		//making a heap
		int d=0;
		for(int i=0;i<inp.size();i++) {
			if(!inp.get(i).isEmpty()) {
			for(int j=0;j<inp.get(i).size();j++) {
					heap.add(new BufSort(inp.get(i).get(j),d));
					d++;
				}
			}
			else {
				inp.remove(i);
			}
		}
		

		int m=0;

		//while heap is not empty
		while(!heap.isEmpty()){
			BufSort b = heap.poll();
			
			//result[m++]=b.arr[b.index];
			System.out.println(b.getVal());
			//if(b.getInd()< ac.arr.length-1){
			//	heap.add(new BufSort(ac.arr, ac.index+1));
			//}
		}
 
		
		/*
		while(!heap.isEmpty()) {
			BufSort b=heap.remove();
			int ind=b.getInd();
			out.add(b.getVal());
			System.out.println(b.getVal());
			if(index[ind]<inp.get(ind).size()) {
				heap.add(new BufSort(inp.get(ind).get(index[ind]),ind));
				index[ind]=++index[ind];
			}
		}
		*/
		
		
	}
}

