package sort;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import inputStream.Mapping.*;

//Written with inspiration by https://github.com/dpkagrawal/kwaymerge/blob/master/src/KWayMerging.java

public class DMergeSort {
	private int B = 10;
	//List<>
	public DMergeSort() {}
	public DMergeSort(List<MapInp> inp, String FN, int B) {
		this.B=B;
		kMerge(inp, FN);
	}
	public DMergeSort(MapInp inp, String FN, int B) {
		List<MapInp> lst = new ArrayList();
		lst.add(inp);
		this.B=B;
		kMerge(lst, FN);
	}
	public void kMerge(List<MapInp> inp, String FN) {
		PriorityQueue<BufSort> heap = new PriorityQueue<BufSort>(inp.size(), new BufSortComparator());
		int i=0;
		MapOut out = new MapOut(FN,B);
		for(MapInp elem : inp) {
			//add only first elements
			if(!elem.endOfStream()) {
				BufSort addNum = new BufSort(inp.get(i).readNext(),i);
				heap.add(addNum);
			}
			i++;
		}
		int a=0;
		while(heap.size()>0) {
			int str = heap.peek().getInd(); // get number of stream
			out.write(heap.poll().getVal()); //get value and remove this element
			if(!inp.get(str).endOfStream()) {
				BufSort addNum = new BufSort(inp.get(str).readNext(),str); //add next element from the stream
				heap.add(addNum);
			}
		}
		out.close();
	}
}

