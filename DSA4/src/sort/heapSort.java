package sort;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import inputStream.Mapping.MapOut;

//Written with inspiration by https://github.com/dpkagrawal/kwaymerge/blob/master/src/KWayMerging.java

public class heapSort {
	private PriorityQueue<BufSort> heap;
	private int strSize=1;
	//List<>
	public heapSort() {
	}
	public void sort(List<Integer> inp) {
		List <Integer> out = new ArrayList<Integer>();
		heap = new PriorityQueue<BufSort>(inp.size(), new BufSortComparator());
		
		//making a heap
		for(int i=0;i<inp.size();i++) {
					heap.add(new BufSort(inp.get(i),i));
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

