package inputStream.Mapping;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MapOut {
	private File inpFile;
	private Integer lastPos=0;
	
	public Integer bufSize=4;
	private Integer pos=0;
	public RandomAccessFile fileS;
	public FileChannel outCh;
	public MappedByteBuffer buffer;
	public MapOut(String inp) {
			open(inp);
	}
	public MapOut(String inp, Integer bf) {
		this.bufSize=bf;
		open(inp);
}
	public void close() {
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
	public void write(Integer num) {
			try {
				if(bufSize-pos<Integer.BYTES) {
					pos=0;
				buffer=outCh.map(FileChannel.MapMode.READ_WRITE, lastPos, bufSize);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		buffer.putInt(num);
		buffer.clear();
		lastPos+=Integer.BYTES;
		pos+=Integer.BYTES;
	}
	
}
