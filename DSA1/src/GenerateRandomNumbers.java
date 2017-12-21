
import java.util.Random;

public class GenerateRandomNumbers  {
 
	
	/*This method id used to generate random 32 bit integers. Generate only positive numbers
    * https://docs.oracle.com/javase/8/docs/api/java/util/Random.html
	* https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
    */
	public static void generateRandomNr ( String nameFile, int N)  {
		
		SystemCallsOutputStream outStrClass = new SystemCallsOutputStream();
		
		outStrClass.create(nameFile);
    
        for (int i = 0; i < N; i++) {
             Random intNr = new Random();
           // int nr = intNr.nextInt(Integer.MAX_VALUE - Integer.MIN_VALUE +1) + Integer.MIN_VALUE;
             int nr = intNr.nextInt(Integer.MAX_VALUE);
            
             outStrClass.write(nr);
         }
         
        outStrClass.close();
         System.out.println("The file with integer number is created");
	}
}

	