package com.sophiego.helper;

import java.io.IOException;
import java.io.RandomAccessFile;

public class LevelWriter {

	public static void writeToPosition(String fileName, String data, long position) throws IOException {
		RandomAccessFile writer = new RandomAccessFile(fileName, "rw");
	    writer.seek(position);
	    writer.writeBytes(data);
	    writer.close();
	}

}
