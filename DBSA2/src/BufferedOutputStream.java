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
	private static DataOutputStream dataOutputStr;
	private static IntBuffer buffer;
	private static FileOutputStream fileOutputStr;
//-------------------------------------------------------------
	public void creat(String fileDir)
	{
		try
		{
			fileOutputStr = new FileOutputStream(new File (fileDir)); 
			dataOutputStr = new DataOutputStream(fileOutputStr); 
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
		/*buffer.flip(); //switch the buffer from reading mode into writing mode
		while (buffer.hasRemaining()) 
		{*/
			try 
			{
			  //temp=buffer.get();
			  dataOutputStr.writeInt(val);
			  //System.out.println("dataOutputStr: " + temp);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		//}
			  //while
		//buffer.clear();	
		/*Each of the primitive buffers provides a set of get() and put() 
		 * methods to read/write an element or a array of elements from/to the buffer. 
		 * The position increases by the number of elements transferred.
		 * https://www.ntu.edu.sg/home/ehchua/programming/java/J5b_IO_advanced.html
		 */
		//buffer.put(output);
		//System.out.println("dataOutputStr: " + output);
		
	}
//----------------------------------------------------------------------------
	public void close() 
	{
		try 
		{	dataOutputStr.close();
		    //buffer.clear();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
//----------------------------------------------------------------------------	
}
