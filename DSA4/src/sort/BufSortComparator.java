package sort;
import java.util.Comparator;


public class BufSortComparator implements Comparator<BufSort>{
	@Override
	public int compare(BufSort a, BufSort b) {
		return a.getVal().compareTo(b.getVal());
	}
}
