package inputStream.Mapping;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MapInp {
	private File inpFile;
	private long fileLen;
	public int pos=0,lastPos=0,bufCap;
	public int bufSize=4;
	private RandomAccessFile fstream;
	private FileChannel inpCh;
	private MappedByteBuffer buffer;
	int lp=0;
	public MapInp(String inp,int b) {
		this.bufSize=b*Integer.BYTES;;
		open(inp);
	}
	public long getFS() {
		return fileLen;
	}
	public void close() {
		try {
			fstream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void open(String fileName) {
		inpFile=new File(fileName);
		try {
			fstream=new RandomAccessFile(inpFile,"rw");
			inpCh=fstream.getChannel();
			fileLen=fstream.length();
			if(fileLen>bufSize) {
				bufCap=bufSize;
			}
			else {
				bufCap=(int)fileLen;
			}
			buffer = inpCh.map(FileChannel.MapMode.READ_WRITE, 0, fstream.length());
		} catch (IOException e) {
			System.out.println("File "+fileName+" not found");
			e.printStackTrace();
		}
	}

	public int readNext() {
		//bufCap=Math.min((int)fileLen-pos-lastPos,bufSize); //current buffer capacity
		if(fileLen-pos-lastPos>bufSize) bufCap = bufSize;
		else bufCap = (int) (fileLen-pos-lastPos);
		if(buffer.position()==buffer.capacity()) {
			lastPos+=pos;
			buffer.clear();
		try {
			buffer = inpCh.map(FileChannel.MapMode.READ_ONLY, lastPos,bufCap);
		}
		catch (IOException e) {
		e.printStackTrace();
		}
		pos=0;
		}
		pos+=4;
		return buffer.getInt();
	}
	public boolean endOfStream() {
		if(fileLen-lastPos-pos>0) return false;
		return true;
	}
	
}
