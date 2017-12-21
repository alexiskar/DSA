import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MapInp {
	private File inpFile;
	long fileLen;
	private int pos=0,lastPos=0,bufCap;
	public int bufSize=16;
	public FileInputStream fstream;
	public FileChannel inpCh;
	public MappedByteBuffer buffer;
	MapInp(String inp) {
			open(inp);
	}
	void close() throws IOException {
		fstream.close();
	}
	void open(String fileName) {
		inpFile=new File(fileName);
		try {
			fstream=new FileInputStream(inpFile);
			inpCh=fstream.getChannel();
			fileLen=inpCh.size();
			if(fileLen>bufSize) {
				bufCap=bufSize;
			}
			else {
				bufCap=(int)fileLen;
			}
			
			buffer = inpCh.map(FileChannel.MapMode.READ_ONLY, 0, fileLen);
		} catch (IOException e) {
			System.out.println("File "+fileName+" not found");
		}
	}
	int readNextInt() {
		int res;
		try {
			bufCap=Math.min((int)fileLen-pos-lastPos,bufSize); //current buffer capacity
		if(buffer.position()==buffer.capacity()) {
			lastPos+=Integer.BYTES;
			buffer.clear();
			inpCh.map(FileChannel.MapMode.READ_ONLY, lastPos,bufCap);
			pos=0;
		}
	}
		catch (IOException e) {
		e.printStackTrace();
	}
		res=buffer.getInt();
		pos+=Integer.BYTES;
		return res;
	}
	public boolean eof() {
		if(fileLen-lastPos-pos>0) return false;
		return true;
	}
	
}
