package com.sprogroup.filequeue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

public class FileQueue {
	private File mFile;
	private String mDataFile;
	private BufferedReader mIs;
	private BufferedWriter mOs;

	public void removeFirstLine(String fileName) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
		// Initial write position
		long writePosition = raf.getFilePointer();
		raf.readLine();
		// Shift the next lines upwards.
		long readPosition = raf.getFilePointer();

		byte[] buff = new byte[1024];
		int n;
		while (-1 != (n = raf.read(buff))) {
			raf.seek(writePosition);
			raf.write(buff, 0, n);
			readPosition += n;
			writePosition += n;
			raf.seek(readPosition);
		}
		raf.setLength(writePosition);
		raf.close();
	}

	public FileQueue(String path) {
		try {
			mDataFile = path;
			mFile = new File(mDataFile);
			mFile.createNewFile();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void enqueue(String val) {

		try {
			mOs = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mDataFile, true)));
			mOs.write(val);
			mOs.newLine();
			mOs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String top() {
		try {
			mIs = new BufferedReader(new FileReader(mDataFile));
			String val = mIs.readLine();
			mIs.close();
			return val;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public String dequeue() {
		try {
			mIs = new BufferedReader(new FileReader(mDataFile));
			String val = mIs.readLine();
			removeFirstLine(mDataFile);
			mIs.close();
			return val;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public boolean isEmpty() {
		return !mFile.exists() || mFile.length() <= 0;
	}
}
