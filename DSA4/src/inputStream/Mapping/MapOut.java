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
		this.bufSize=bf*Integer.BYTES;
		open(inp);
	}
	public void close() {
		try {
			fileS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void open(String fileName) {
			try {
				inpFile=new File(fileName);
				if(!inpFile.exists()) {
					inpFile.createNewFile();
				}
				fileS = new RandomAccessFile(inpFile, "rw");
				outCh=fileS.getChannel();
				buffer = outCh.map(FileChannel.MapMode.READ_WRITE, lastPos, bufSize);
			} catch (IOException e) {
				e.printStackTrace();
			}		
	}
	public void write(int num) {
				if(bufSize-pos<Integer.BYTES) {
					lastPos+=pos;
					buffer.clear();
					pos=0;
					try {
						fileS.setLength(lastPos+bufSize);
						//extend mapped area if necessary
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						buffer=outCh.map(FileChannel.MapMode.READ_WRITE, lastPos, bufSize);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		buffer.putInt(num);
		pos+=Integer.BYTES;
	}
	
}
