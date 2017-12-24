package inputStream.Mapping;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MapInp {
	private File inpFile;
	private long fileLen;
	private int pos=0,lastPos=0,bufCap;
	private int bufSize=4;
	private RandomAccessFile fstream;
	private FileChannel inpCh;
	private MappedByteBuffer buffer;
	public MapInp(String inp,int b) {
		this.bufSize=b*Integer.BYTES;;
	//	open(inp);
	}
	public void close() throws IOException {
		fstream.close();
	}
	public void open(String fileName) {
		inpFile=new File(fileName);
		try {
			inpFile = new File(fileName);
			fstream=new RandomAccessFile(inpFile,"r");
			inpCh=fstream.getChannel();
			fileLen=inpCh.size();
			if(fileLen>bufSize) {
				bufCap=bufSize;
			}
			else {
				bufCap=(int)fileLen;
			}
			buffer = inpCh.map(FileChannel.MapMode.READ_ONLY, 0, fstream.length());
		} catch (IOException e) {
			System.out.println("File "+fileName+" not found");
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
