package com.antfact.twitter.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class StringCompress {
	public static final byte[] compress(String paramString) {
		if (paramString == null)
			return null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		ZipOutputStream zipOutputStream = null;
		byte[] arrayOfByte;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
			zipOutputStream.putNextEntry(new ZipEntry("0"));
			zipOutputStream.write(paramString.getBytes());
			zipOutputStream.closeEntry();
			arrayOfByte = byteArrayOutputStream.toByteArray();
		} catch (IOException localIOException5) {
			arrayOfByte = null;
		} finally {
			if (zipOutputStream != null)
				try {
					zipOutputStream.close();
				} catch (IOException localIOException6) {
				}
			if (byteArrayOutputStream != null)
				try {
					byteArrayOutputStream.close();
				} catch (IOException localIOException7) {
				}
		}
		return arrayOfByte;
	}

	public static final byte[] compressByte(byte[] paramString) {
		if (paramString == null)
			return null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		ZipOutputStream zipOutputStream = null;
		byte[] arrayOfByte;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			zipOutputStream = new ZipOutputStream(byteArrayOutputStream);

//			zipOutputStream.setMethod(ZipOutputStream.DEFLATED);
//			zipOutputStream.setLevel(Deflater.DEFAULT_COMPRESSION);
//			zipOutputStream.setLevel(Deflater.BEST_COMPRESSION);
//			zipOutputStream.setLevel(-1);

			zipOutputStream.putNextEntry(new ZipEntry("0"));
			zipOutputStream.write(paramString);
			zipOutputStream.closeEntry();
			arrayOfByte = byteArrayOutputStream.toByteArray();
		} catch (IOException localIOException5) {
			arrayOfByte = null;
		} finally {
			if (zipOutputStream != null)
				try {
					zipOutputStream.close();
				} catch (IOException localIOException6) {
				}
			if (byteArrayOutputStream != null)
				try {
					byteArrayOutputStream.close();
				} catch (IOException localIOException7) {
				}
		}
		return arrayOfByte;
	}

	@SuppressWarnings("unused")
	public static final String decompress(byte[] paramArrayOfByte) {
		if (paramArrayOfByte == null)
			return null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		ByteArrayInputStream byteArrayInputStream = null;
		ZipInputStream zipInputStream = null;
		String str;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			byteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
			zipInputStream = new ZipInputStream(byteArrayInputStream);
			ZipEntry localZipEntry = zipInputStream.getNextEntry();
			byte[] arrayOfByte = new byte[1024];
			int i = -1;
			while ((i = zipInputStream.read(arrayOfByte)) != -1)
				byteArrayOutputStream.write(arrayOfByte, 0, i);
			str = byteArrayOutputStream.toString();
		} catch (IOException localIOException7) {
			str = null;
		} finally {
			if (zipInputStream != null)
				try {
					zipInputStream.close();
				} catch (IOException localIOException8) {
				}
			if (byteArrayInputStream != null)
				try {
					byteArrayInputStream.close();
				} catch (IOException localIOException9) {
				}
			if (byteArrayOutputStream != null)
				try {
					byteArrayOutputStream.close();
				} catch (IOException localIOException10) {
				}
		}
		return str;
	}

	@SuppressWarnings("unused")
	public static final byte[] decompressByte(byte[] paramArrayOfByte) {
		if (paramArrayOfByte == null)
			return null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		ByteArrayInputStream byteArrayInputStream = null;
		ZipInputStream zipInputStream = null;
		byte[] str;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			byteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
			zipInputStream = new ZipInputStream(byteArrayInputStream);
			ZipEntry localZipEntry = zipInputStream.getNextEntry();
			byte[] arrayOfByte = new byte[1024];
			int i = -1;
			while ((i = zipInputStream.read(arrayOfByte)) != -1)
				byteArrayOutputStream.write(arrayOfByte, 0, i);
			str = byteArrayOutputStream.toByteArray();
		} catch (IOException localIOException7) {
			str = null;
		} finally {
			if (zipInputStream != null)
				try {
					zipInputStream.close();
				} catch (IOException localIOException8) {
				}
			if (byteArrayInputStream != null)
				try {
					byteArrayInputStream.close();
				} catch (IOException localIOException9) {
				}
			if (byteArrayOutputStream != null)
				try {
					byteArrayOutputStream.close();
				} catch (IOException localIOException10) {
				}
		}
		return str;
	}

//	public static byte[] gzip(byte[] content) throws IOException {
	public static byte[] gzip(byte[] content) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPOutputStream gos;
		try {
			gos = new GZIPOutputStream(baos);

			ByteArrayInputStream bais = new ByteArrayInputStream(content);
			byte[] buffer = new byte[1024];
			int n;
			while ((n = bais.read(buffer)) != -1) {
				gos.write(buffer, 0, n);
			}
			gos.flush();
			gos.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//			ApiLogger.error("gzip compress error.", e);
		}
		return baos.toByteArray();
	}

	public static byte[] unGzip(byte[] content) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(content));
		byte[] buffer = new byte[1024];
		int n;
		while ((n = gis.read(buffer)) != -1) {
			baos.write(buffer, 0, n);
		}

		return baos.toByteArray();
	}

	public static String byteToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer(bytes.length);
		String sTemp;
		for (int i = 0; i < bytes.length; i++) {
			sTemp = Integer.toHexString(0xFF & bytes[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {

//		String str = "abcdefghijkaaazzzzzzzzzaaaaaaaaaaaaaadfdsfdsfdfdaaaaaaaaaaasfsda";
		String str = "{\\\"androidSdk\\\":22,\\\"androidVer\\\":\\\"5.1\\\",\\\"cpTime\\\":1612071603,{\\\"androidSdk\\\":22,\\\"androidVer\\\":\\\"5.1\\\",\\\"cpTime\\\":1612071603,{\\\"androidSdk\\\":22,\\\"androidVer\\\":\\\"5.1\\\",\\\"cpTime\\\":1612071603,\"cupABIs\":[\"armeabi-v7a\",\"armeabi\"],\"customId\":\"QT99999\",\"elfFlag\":false,\"id\":\"4a1b644858d83a98\",\"imsi\":\"460015984967892\",\"system\":true,\"systemUser\":true,\"test\":true,\"model\":\"Micromax R610\",\"netType\":0,\"oldVersion\":\"0\",\"pkg\":\"com.adups.fota.sysoper\",\"poll_time\":30,\"time\":1481634113876,\"timeZone\":\"Asia\\/Shanghai\",\"versions\":[{\"type\":\"gatherApks\",\"version\":1},{\"type\":\"kernel\",\"version\":9},{\"type\":\"shell\",\"version\":10},{\"type\":\"silent\",\"version\":4},{\"type\":\"jarUpdate\",\"version\":1},{\"type\":\"serverIps\",\"version\":1}]}";

		byte[] bytes = str.getBytes();

		System.out.println("压缩前长度：" + bytes.length);
		byte[] gzipBytes = gzip(bytes);

		String ysb = (gzipBytes.length * 100) / bytes.length + "%";

		System.out.println("压缩后长度：" + gzipBytes.length + " 压缩比：" + ysb);
		System.out.println("压缩后：" + byteToHexString(gzipBytes));
		byte[] unGzipBytes = unGzip(gzipBytes);
		System.out.println("解压后：" + byteToHexString(unGzipBytes));

		System.out.println("---------");

		System.out.println("压缩前长度：" + bytes.length);
		byte[] gzipBytes2 = compressByte(bytes);
		String ysb2 = (gzipBytes2.length * 100) / bytes.length + "%";
		System.out.println("压缩后长度：" + gzipBytes2.length + " 压缩比：" + ysb2);
		System.out.println("压缩后：" + byteToHexString(gzipBytes2));
		byte[] unGzipBytes2 = decompressByte(gzipBytes2);
		System.out.println("解压后：" + byteToHexString(unGzipBytes2));

	}

}
