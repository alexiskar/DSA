import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.Random;

public class Main {

	public static void main(String[] args)throws IOException
	{
		BufferedInputStream inputClass = new BufferedInputStream();
		BufferedOutputStream outputClass = new BufferedOutputStream();
		IntBuffer buffer;
		int bufferSize=10;
		int randomInt=0;
		Random randomNum =new Random();
		
		
        //create a file on disk with 32bit integers
	      
        FileOutputStream fos = new FileOutputStream(new File("data/input.data"));
        DataOutputStream dos = new DataOutputStream(fos);
        
             for (int i = 0; i < 5; i++) {
                 randomInt = randomNum.nextInt(2147483647);
                 //System.out.println(randomInt);
                 dos.writeInt(randomInt);
             }
             dos.flush();
		
		
		//buffer = IntBuffer.allocate(bufferSize); 
		//b2=IntBuffer.allocate(bufferSize);
		inputClass.open("data/input.data");
		outputClass.creat("data/output.data");
		while (!inputClass.end_of_Stream())
		{
			int val=inputClass.read_Next();
			//System.out.println(val);
			outputClass.write(val);
			/*if (buffer.capacity() == buffer.position())
			{
				outputClass.write(buffer);
			}*/
			
		}

		//outputClass.close(buffer);

	}

}
