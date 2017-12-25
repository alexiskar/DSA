package sort;
import java.nio.BufferUnderflowException;
import java.util.Comparator;
import inputStream.Mapping.*;

public class extComparator implements Comparator<MapInp>{

	@Override
	public int compare(MapInp o1, MapInp o2) {
		Integer a=0;
		try {
			a=o1.readNext();
		}
		catch (BufferUnderflowException e) {
			
		}
		//o1.back(a);
		//return int to stream
		Integer b =0;
		try {
			b=o2.readNext();
		}
		catch (BufferUnderflowException e) {
			
		}
		//return int to stream
		return a.compareTo(b);
	}
}
