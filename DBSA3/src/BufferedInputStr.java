import java.io.*;

public class BufferedInputStr 
{
	//Variables----------------------------------------------------------------------------
	private  BufferedInputStream bufferedInputStr=null;
	private  FileInputStream fileInputStr=null;
	private  DataInputStream dataInputStr=null;
	//----------------------------------------------------------------------------
	public void open(String fileDir)
	{
		try
		{
			fileInputStr = new FileInputStream(new File (fileDir)); 
			bufferedInputStr = new BufferedInputStream(fileInputStr);
			dataInputStr = new DataInputStream(bufferedInputStr);

		}
		catch (FileNotFoundException e)
		{ 
			System.out.println("Input File does not exist !");
			System.exit(0);
		}
	}
//----------------------------------------------------------------------------
	public void read_Next() 
	{
		try 
		{
			while (dataInputStr.available() != 0)
			{
				System.out.println("dataInputStr: " + dataInputStr.readInt());
			}

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
//----------------------------------------------------------------------------
	public boolean end_of_Stream() 
	{
		try 
		{	
			if (dataInputStr.available() > 0)
				return false;
			else
			{  
				close_Streams() ;
				return true;
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			close_Streams();
			return true;
		}
	}
//----------------------------------------------------------------------------	
	public void close_Streams() 
	{
		try 
		{
			if (bufferedInputStr != null)  
			bufferedInputStr.close(); 
			if (fileInputStr != null)  
			fileInputStr.close(); 
			if (dataInputStr != null)  
			dataInputStr.close(); 
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
//----------------------------------------------------------------------------	
}
