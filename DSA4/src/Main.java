
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Main {


	  public static void main(String[] args) throws IOException {

		  MapOut mo = new MapOut("data/out.data");
		  List <Integer> a=new ArrayList(),b=new ArrayList();

        for (int i = 0; i < 1000000; i++) {
             Random intNr = new Random();
             int nr = intNr.nextInt(Integer.MAX_VALUE);
             a.add(new Integer(nr));
             mo.write(nr);
         }
        MapInp mi = new MapInp("data/out.data");
		  
		while(!mi.eof()) {
			b.add(new Integer(mi.readNextInt()));
		}
		
		for(int i=0;i<a.size();i++) {
			System.out.println(a.get(i)+" "+b.get(i)); 
			if(a.get(i)==b.get(i)) { System.out.println("error "+i);}
		}
	  }
}
