import java.io.*;
import java.util.Random;


public class Main 
{

	static int size=10000000;
	
	
	public static void main(String[] args) throws IOException 
	{
		BufferedInputStr inputClass = new BufferedInputStr();
		BufferedOutputStr outputClass = new BufferedOutputStr();
		int randomInt=0;
		Random randomNum =new Random();
		//-----------------------------------------------------------
		CreatRandomNumbersForFile();
		//Read data from file
		inputClass.open("data/input.data");
		while (!inputClass.end_of_Stream())
		{
			//System.out.println("Starts Reading From Input File...");
			inputClass.read_Next();
		}

		//--------------------------------------------------------------
		//System.out.println("Random numbers are created to be saved in a Output File");
		while (size>0)
		{
			randomInt = randomNum.nextInt(2147483647);
	    	System.out.println(randomInt);
			outputClass.creat("data/output.data");
			outputClass.write(randomInt);
			outputClass.close();
			size--;
		}
		

	}
//-----------------------------------------------------------
	public static void CreatRandomNumbersForFile() throws IOException
	{
		FileOutputStream fileOutputStr=null;
		DataOutputStream dataOutputStr=null;
		int randomInt=0, i=0;
		Random randomNum =new Random();
		try
		{
			fileOutputStr = new FileOutputStream(new File("data/input.data"));
			dataOutputStr = new DataOutputStream(fileOutputStr); 
			//System.out.println("Random numbers are created to be saved in an Input File");
	        for (i = 0; i < size; i++) 
	        {
	        	randomInt = randomNum.nextInt(2147483647);
	        	dataOutputStr.writeInt(randomInt);
	        	System.out.println(randomInt);
	        }
	        dataOutputStr.flush();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Input File does not exist !");
		}
	}
//------------------------------------------------------------------------

}
