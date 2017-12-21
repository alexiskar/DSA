import java.io.File;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.IntBuffer; 
/* This class defines four categories of operations upon int buffers
*https://docs.oracle.com/javase/7/docs/api/java/nio/IntBuffer.html
*/

public class BufferedInputStream 
{
	//Variables
	private  DataInputStream dataInputStr=null;
	//private  IntBuffer buffer;
	private  FileInputStream fileInputStr=null;
//-------------------------------------------------------------
	public void open(String fileDir, IntBuffer buffer)
	{
		try
		{
			fileInputStr = new FileInputStream(new File (fileDir)); 
			dataInputStr = new DataInputStream(fileInputStr);
			//buffer = IntBuffer.allocate(bufferSize); 
			/*The allocate() method allocate a int buffer of given capacity. 
			 * https://www.roseindia.net/tutorial/java/corejava/nio/AllocateIntBuffer.html
			 */
		}
		catch (FileNotFoundException e)
		{ 
			System.out.println("Input File does not exist !");
			System.exit(0);
		}
	}
//----------------------------------------------------------------------------
	public int read_Next(IntBuffer buffer) 
	{
		int temp=0;
		/* Unlike array, there is a so-called position in a Buffer that 
		 * indicates where the next piece of data is to be read or written. You can 
		 * retrieve the current position via method position() or change the current 
		 * position via method position(int newPosition). 
		 * Position shall not be greater than the limit.
		 * 
		 * The capacity must be specified when the Buffer is constructed and cannot be changed
	     * (similar to an array). You can retrieve it via method capacity().
	     * https://www.ntu.edu.sg/home/ehchua/programming/java/J5b_IO_advanced.html
		 */
		if (buffer.capacity() == buffer.position())
			buffer.clear();
		try 
		{
			/*Each of the primitive buffers provides a set of get() and put() 
			 * methods to read/write an element or a array of elements from/to the buffer. 
			 * The position increases by the number of elements transferred.
			 * https://www.ntu.edu.sg/home/ehchua/programming/java/J5b_IO_advanced.html
			 */
			temp=dataInputStr.readInt();
			buffer.put(temp);
			System.out.println("dataInputStr: " + temp);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			/*printStackTrace is very useful tool for diagnosing an Exception. 
			It tells you what happened and where in the code this happened.
			https://stackoverflow.com/questions/2560368/what-is-the-use-of-printstacktrace-method-in-java
			*/
		}
		return buffer.get(buffer.position()-1);
	}
//----------------------------------------------------------------------------
	public boolean end_of_Stream() 
	{
		try 
		{	/*if buffer has content to read - Returns: an estimate of the number 
		    * of bytes that can be read
		    * https://stackoverflow.com/questions/21216476/check-if-datainputstream-has-content
		    */
			if (dataInputStr.available() > 0)
				return false;
			else
				return true;
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			return true;
		}
	}
//----------------------------------------------------------------------------	
}
