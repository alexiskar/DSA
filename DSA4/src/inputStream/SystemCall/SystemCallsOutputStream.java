package inputStream.SystemCall;
/* This class implements the operations for the OUTPUT STREAM using SYSTEM CALLS
 */

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



public class SystemCallsOutputStream {

	
	private FileOutputStream fileOutputStr;
	private DataOutputStream dataOutputStr;
		
	
	// This method create a new file for writing
	public void create(String nameFile){
		try{
			fileOutputStr= new FileOutputStream(new File(nameFile));
			dataOutputStr = new DataOutputStream(fileOutputStr);
			}
		catch (FileNotFoundException e){
			System.out.println("Unable to create output File!");
			e.printStackTrace();
		}
	}
    	
	
	// This method write an element to the stream
    public void write(int value){
		try {
			dataOutputStr.writeInt(value);
			}
		catch (IOException e) {
				System.out.println("Unable to write in the output File!");
			    e.printStackTrace();
			}
	}
        
	
    
	// This method is used to close the output stream
    public void close() {
		try {
			dataOutputStr.close();
			}
		catch (IOException e) {
			e.printStackTrace();
			}
	}
		
		
}