import java.io.*;

public class BufferedOutputStr 
{
	//Variables----------------------------------------------------------------------------
		private  BufferedOutputStream bufferedOutputStr=null;
		private  FileOutputStream fileOutputStr=null;
		private  DataOutputStream dataOutputStr=null;
		//----------------------------------------------------------------------------
		public void creat(String fileDir)
		{
			try
			{
				fileOutputStr = new FileOutputStream(new File (fileDir)); 
				bufferedOutputStr = new BufferedOutputStream(fileOutputStr);
				dataOutputStr = new DataOutputStream(bufferedOutputStr);
			}
			catch (FileNotFoundException e)
			{ 
				System.out.println("Input File does not exist !");
			}
		}
		//----------------------------------------------------------------------------
		public void write(int output) 
		{
			try 
			{
				System.out.println("dataOutputStr: " + output);
				dataOutputStr.writeInt(output);
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
		}
	//----------------------------------------------------------------------------
		public void close() 
		{
			try 
			{	if (bufferedOutputStr != null)  
				bufferedOutputStr.close(); 
				if (fileOutputStr != null)  
				fileOutputStr.close(); 
				if (dataOutputStr != null)  
				dataOutputStr.close(); 
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	//----------------------------------------------------------------------------	
		
}
