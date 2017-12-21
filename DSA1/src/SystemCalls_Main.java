/* This class uses read data from and write data to disk using the SYSTEM CALLS
 */

public class SystemCalls_Main {

	  public static void main(String[] args) {
	        
		  SystemCallsInputStream inStrClass = new SystemCallsInputStream();
		  SystemCallsOutputStream outStrClass = new SystemCallsOutputStream();

		  GenerateRandomNumbers.generateRandomNr ("data/input.data", 5);
	                  
		  inStrClass.open("data/input.data");     
		  outStrClass.create("data/output.data");   
		  
		  int inValue;
		  while (!inStrClass.endOfStream())
		    {  
			  inValue = inStrClass.readNext();
			  outStrClass.write(inValue);
			  System.out.println("The value that is write is " + inValue);
		    }
	       outStrClass.close();

        
	}    
	        
}

