package inputStream.Fread;

import java.io.File;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedInputStream;

/* This class defines four categories of operations upon int buffers
*https://docs.oracle.com/javase/7/docs/api/java/nio/IntBuffer.html
*/


public class FreadStream 
{
	//Variables
	private  DataInputStream dataInputStr=null;
	private  FileInputStream fileInputStr=null;
	private BufferedInputStream bufIS;
//-------------------------------------------------------------
	public void open(String fileDir)
	{
		try
		{
			fileInputStr = new FileInputStream(new File (fileDir));
			bufIS = new BufferedInputStream(fileInputStr);
			dataInputStr = new DataInputStream(bufIS);
		}
		catch (FileNotFoundException e)
		{ 
			System.out.println("Input File does not exist !");
			System.exit(0);
		}
	}
//----------------------------------------------------------------------------
	public int readNext() 
	{
		int inpVal=0;
		try 
		{
			inpVal=dataInputStr.readInt();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return inpVal;
	}
//----------------------------------------------------------------------------
	public boolean endOfStream() 
	{
		try 
		{	
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
