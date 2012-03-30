package gp5;

import java.io.*;

/**
 * The class GP5FileOutputStream contains all methods to write necessary information to gp5 file format
 * 
 * @author Julian (TuxGuitar)
 * @author Deniss Paltovs (KACAH)
 */
class GP5FileOutputStream extends FileOutputStream {
	
	public GP5FileOutputStream(String fileName) throws FileNotFoundException {
		super(fileName);
	}
	
	public void writeNulls(int count) throws IOException {
		for(int i = 0; i < count; i++) {
			this.write(0);
		}
	}
	
	public void writeBoolean(boolean v) throws IOException {
		write(v ? 1 : 0);
	}
	
	protected void writeInt(int v) throws IOException {
		byte[] bytes = { (byte)(v & 0x00FF),(byte)((v >> 8) & 0x000000FF),(byte) ((v >> 16) & 0x000000FF),(byte)((v >> 24) & 0x000000FF) };
		write(bytes);
	}
	
	public void writeBytes(byte[] bytes, int maxLen) throws IOException {
		int realLen = maxLen > bytes.length ? bytes.length : maxLen;
		for(int i = 0 ; i < realLen; i++) {
			write( bytes[i] );
		}
	}
	
	public void writeStringByte(String string, int size) throws IOException {
		byte[] bytes = string.getBytes( GP5Defaults.CHARSET );
		this.write( (byte)( size == 0 || size > bytes.length ? bytes.length : size ));
		writeBytes( bytes, size );
		writeNulls( size - bytes.length );
	}
	
	public void writeStringByte(String string) throws IOException {
		writeStringByte(string, string.length());
	}
	
	public void writeStringInt(String string) throws IOException {
		byte[] bytes = string.getBytes(GP5Defaults.CHARSET);
		this.writeInt( bytes.length+1 );
		writeStringByte(string, bytes.length);
	}
}
