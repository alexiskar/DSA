import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MapOut {
	private File inpFile;
	private int lastPos=0;
	public int bufSize=4;
	public RandomAccessFile fileS;
	public FileChannel outCh;
	public MappedByteBuffer buffer;
	MapOut(String inp) {
			open(inp);
	}
	void close() {
		try {
			fileS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	void open(String fileName) {
			try {
				inpFile=new File(fileName);
				if(!inpFile.exists()) {
					inpFile.createNewFile();
				}
				fileS = new RandomAccessFile(inpFile, "rw");
				outCh=fileS.getChannel();
				buffer = outCh.map(FileChannel.MapMode.READ_WRITE, 0, bufSize);
			} catch (IOException e) {
				e.printStackTrace();
			}		
	}
	void write(int num) {
			try {
				buffer=outCh.map(FileChannel.MapMode.READ_WRITE, lastPos, bufSize);
			} catch (IOException e) {
				e.printStackTrace();
			}
		buffer.putInt(num);
		buffer.clear();
		lastPos+=Integer.BYTES;;
	}
	
}
