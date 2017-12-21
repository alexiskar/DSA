/* This class implements the operations for the INPUT STREAM using SYSTEM CALLS
 */

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class SystemCallsInputStream {

	
	private InputStream fileInputStr;
	private  DataInputStream dataInputStr;
		
	
	// This method open an existing file for reading
	public void open(String nameFile){
		try{
			fileInputStr = new FileInputStream(new File(nameFile));
			dataInputStr = new DataInputStream(fileInputStr);
			}
		catch (FileNotFoundException e){
			System.out.println("Unable to find input File!");
			e.printStackTrace();
		}
	}
    	
	
	// This method read the next element from the stream
    public int readNext(){
		int readValue = 0;
		try {
			readValue = dataInputStr.readInt();
			}
		catch (IOException e) {
				System.out.println("Unable to read from input File!");
			    e.printStackTrace();
			}
		return readValue;
	}
        
	
	// This method is used to check if the end of the input stream has been reached
    
    /* available() - Returns number of remaining bytes that can be read (or skipped over)
     * from this input stream without blocking by the next invocation of a method for this input stream.
     * https://docs.oracle.com/javase/7/docs/api/java/io/InputStream.html
     */
    
    public boolean endOfStream() {
		try {
			 if (dataInputStr.available()>0) 
				return false;
			 else
				 return true;
			}
		catch (IOException e) {
			e.printStackTrace();
			  return true;
			}
	}
		
		
}