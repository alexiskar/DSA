package sort;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

//Written with inspiration by https://github.com/dpkagrawal/kwaymerge/blob/master/src/KWayMerging.java

public class DMergeSort {
	private PriorityQueue<BufSort> heap;

	public void kMerge(List<List<Integer>> inp) {
		List <Integer> out = new ArrayList<Integer>();
		heap = new PriorityQueue<BufSort>(inp.size(), new BufSortComparator());
		int[] index = new int[inp.size()];

		
		//making a heap
		for(int i=0;i<inp.size();i++) {
			if(!inp.get(i).isEmpty()) {
				heap.add(new BufSort(inp.get(i).get(0),i));
				index[i]=++index[i];
			}
			else {
				inp.remove(i);
			}
		}
		
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
	}
}

