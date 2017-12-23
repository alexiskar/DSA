package inputStream.Buffer;

import java.io.File;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.IntBuffer; 
/* This class defines four categories of operations upon int buffers
*https://docs.oracle.com/javase/7/docs/api/java/nio/IntBuffer.html
*/

public class BufferedOutputStream 
{
	//Variables
	private DataOutputStream dataOutputStr;
	private IntBuffer buffer;
	private int bufSize=4;
	private FileOutputStream fileOutputStr;
//-------------------------------------------------------------
	public void create(String fileDir)
	{
		try
		{
			fileOutputStr = new FileOutputStream(new File (fileDir)); 
			dataOutputStr = new DataOutputStream(fileOutputStr);
			buffer = IntBuffer.allocate(bufSize);
			/*The allocate() method allocate a int buffer of given capacity. 
			 * https://www.roseindia.net/tutorial/java/corejava/nio/AllocateIntBuffer.html
			 */
		}
		catch (FileNotFoundException e)
		{ 
			System.out.println("Input File does not exist !");
		}
	}
//----------------------------------------------------------------------------
	public void write(int val) 
	{
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
		if(buffer.position()==buffer.capacity()) {
			//check whether buffer is full
		buffer.flip(); 
		//switch the buffer from reading mode into writing mode
		while (buffer.hasRemaining()) 
		{
			try 
			{
			  dataOutputStr.writeInt(buffer.get());
			  //System.out.println("dataOutputStr: " + temp);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
			  //while
		buffer.clear();	
		}
		buffer.put(val);
		/*Each of the primitive buffers provides a set of get() and put() 
		 * methods to read/write an element or a array of elements from/to the buffer. 
		 * The position increases by the number of elements transferred.
		 * https://www.ntu.edu.sg/home/ehchua/programming/java/J5b_IO_advanced.html
		 */
	}
//----------------------------------------------------------------------------

	public void SetBufSize(int bufferSize) {
		this.bufSize=bufferSize;
	}
	public int GetBufSize() {
		return bufSize;
	}
	
	public void close() 
	{
	//write integers which are left in the buffer
		if(buffer.position()>0) {
			
			buffer.flip(); 
			//switch the buffer from reading mode into writing mode
			while (buffer.hasRemaining()) 
			{
				try 
				{
				  dataOutputStr.writeInt(buffer.get());
				  //System.out.println("dataOutputStr: " + temp);
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
				  //while
			buffer.clear();	
		}
		try 
		{	dataOutputStr.close();
		    buffer.clear();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
//----------------------------------------------------------------------------	
}
