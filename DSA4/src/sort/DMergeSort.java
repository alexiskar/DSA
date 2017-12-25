package sort;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import inputStream.Mapping.MapOut;

//Written with inspiration by https://github.com/dpkagrawal/kwaymerge/blob/master/src/KWayMerging.java

public class DMergeSort {
	private PriorityQueue<BufSort> heap;
	private int strSize=1;
	//List<>
	public DMergeSort() {
	}
	public void kMerge(List<List<Integer>> inp) {
		List <Integer> out = new ArrayList<Integer>();
		heap = new PriorityQueue<BufSort>(inp.size(), new BufSortComparator());
		
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
	}
	public void outputResult(String FN,int b) {
		MapOut mo = new MapOut(FN,b);
		while(!heap.isEmpty()) {
			mo.write(heap.poll().getVal());
		}
		mo.close();
	}
}

