package com.mbgs.chipsy;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class RAM {

	private char[] RAM4K = new char[4096];//33554432 //4096
	private int RAMMode;
	private char offset = 0x200;
	private char[] RAM32M;// = new char[a lot];
	byte[] byte2 = new byte[2];
	byte[] byte3 = new byte[3];
	private ByteBuffer buf2;
	private File currentRom;
	private ByteBuffer buf3;

	public final static char[] fontset =
			{
					0xF0, 0x90, 0x90, 0x90, 0xF0, // 0
					0x20, 0x60, 0x20, 0x20, 0x70, // 1
					0xF0, 0x10, 0xF0, 0x80, 0xF0, // 2
					0xF0, 0x10, 0xF0, 0x10, 0xF0, // 3
					0x90, 0x90, 0xF0, 0x10, 0x10, // 4
					0xF0, 0x80, 0xF0, 0x10, 0xF0, // 5
					0xF0, 0x80, 0xF0, 0x90, 0xF0, // 6
					0xF0, 0x10, 0x20, 0x40, 0x40, // 7
					0xF0, 0x90, 0xF0, 0x90, 0xF0, // 8
					0xF0, 0x90, 0xF0, 0x10, 0xF0, // 9
					0xF0, 0x90, 0xF0, 0x90, 0x90, // A
					0xE0, 0x90, 0xE0, 0x90, 0xE0, // B
					0xF0, 0x80, 0x80, 0x80, 0xF0, // C
					0xE0, 0x90, 0x90, 0x90, 0xE0, // D
					0xF0, 0x80, 0xF0, 0x80, 0xF0, // E
					0xF0, 0x80, 0xF0, 0x80, 0x80  // F
			};

	public final static char[] HDFontset =
			{
					0xF0, 0xF0, 0x90, 0x90, 0x90, 0x90, 0x90, 0x90, 0xF0, 0xF0, // 0

					0x20, 0x20, 0x60, 0x60, 0x20, 0x20, 0x20, 0x20, 0x70, 0x70, // 1

					0xF0, 0xF0, 0x10, 0x10, 0xF0, 0xF0, 0x80, 0x80, 0xF0, 0xF0, // 2

					0xF0, 0xF0, 0x10, 0x10, 0xF0, 0xF0, 0x10, 0x10, 0xF0, 0xF0, // 3

					0x90, 0x90, 0x90, 0x90, 0xF0, 0xF0, 0x10, 0x10, 0x10, 0x10, // 4

					0xF0, 0xF0, 0x80, 0x80, 0xF0, 0xF0, 0x10, 0x10, 0xF0, 0xF0, // 5

					0xF0, 0xF0, 0x80, 0x80, 0xF0, 0xF0, 0x90, 0x90, 0xF0, 0xF0, // 6

					0xF0, 0xF0, 0x10, 0x10, 0x20, 0x20, 0x40, 0x40, 0x40, 0x40, // 7

					0xF0, 0xF0, 0x90, 0x90, 0xF0, 0xF0, 0x90, 0x90, 0xF0, 0xF0, // 8

					0xF0, 0xF0, 0x90, 0x90, 0xF0, 0xF0, 0x10, 0x10, 0xF0, 0xF0, // 9

					0xF0, 0xF0, 0x90, 0x90, 0xF0, 0xF0, 0x90, 0x90, 0x90, 0x90, // A

					0xE0, 0xE0, 0x90, 0x90, 0xE0, 0xE0, 0x90, 0x90, 0xE0, 0xE0, // B

					0xF0, 0xF0, 0x80, 0x80, 0x80, 0x80, 0x80, 0x80, 0xF0, 0xF0, // C

					0xE0, 0xE0, 0x90, 0x90, 0x90, 0x90, 0x90, 0x90, 0xE0, 0xE0, // D

					0xF0, 0xF0, 0x80, 0x80, 0xF0, 0xF0, 0x80, 0x80, 0xF0, 0xF0, // E

					0xF0, 0xF0, 0x80, 0x80, 0xF0, 0xF0, 0x80, 0x80, 0x80, 0x80  // F
			};

	public RAM(int a) {
		this.offset = (char) a;
		this.buf2 = ByteBuffer.allocate(2);
		this.buf3 = ByteBuffer.allocate(3);

		this.buf2 = ByteBuffer.allocate(2);
		this.buf2.order(ByteOrder.LITTLE_ENDIAN);
		this.buf3 = ByteBuffer.allocate(3);
		this.buf3.order(ByteOrder.LITTLE_ENDIAN);

	}


	public void wipeRAM() {
		for (int i = 0; i < this.RAM4K.length; i++) {
			this.RAM4K[i] = 0;
		}
	}

	public void loadFont() {
		for (int i = 0; i < 80; i++) {
			RAM4K[0x50 + i] = fontset[i];
		}
		for (int i = 0; i < 160; i++) {
			RAM4K[0x100 + i] = HDFontset[i];
		}
	}

	public int getLength() {
		return this.RAM4K.length;
	}

	public char getByte(int i) {
		return this.RAM4K[i];
	}

	public void setByte(int i, char a) {
		this.RAM4K[i] = a;
	}

	public int getBits(int i, int offset) {
		if (offset % 2 == 0) {
			return this.RAM4K[i + offset / 2] >> 4;
		} else {
			return this.RAM4K[i + offset / 2] & 0xF;
		}
	}

	public char getOpcode(int i) {
		char opc = (char) ((RAM4K[i] << 8) | (RAM4K[i + 1]));
		return opc;
	}

	public void softReset() {
		this.wipeRAM();
		//this.loadAtRAM(offset, this.currentRom);

	}


	public short get2Byte(int i) {
		return (short) ((this.RAM4K[i] << 8) | (this.RAM4K[i + 1]));
	}

	public void load2(Context context, int offset, String romNameMain) {
		InputStream input = null;
		AssetManager assManager = context.getAssets();

		try {

			input = context.getAssets().open(romNameMain);
			byte rom[] = getBytes(input);
			Chipsy.myChipsy8.chipCPU.setHash(Arrays.hashCode(rom));

			for (int i = offset; i < rom.length+offset; i++) {
				this.RAM4K[i] = (char) (rom[i-0x200]&0xFF );

			}
		} catch (IOException e) {
			//log the exception
			Log.v("ERRORE", "Rrroerer" + e);
		} finally {
			//Log.v("test", "test");
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					//log the exception
					Log.v("test", e.toString());
				}
			}
		}
		//this.currentRom=rom;
	}


	//public short getMegaByte(int i){
	//int length = buffer.getShort(0) & 0xFFFF;
	//}

	public static byte[] getBytes(InputStream is) throws IOException {

		int len;
		int size = 1024;
		byte[] buf;

		if (is instanceof ByteArrayInputStream) {
			size = is.available();
			buf = new byte[size];
			len = is.read(buf, 0, size);
		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			buf = new byte[size];
			while ((len = is.read(buf, 0, size)) != -1)
				bos.write(buf, 0, len);
			buf = bos.toByteArray();
		}
		return buf;

	}
}
