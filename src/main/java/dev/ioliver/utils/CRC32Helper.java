package dev.ioliver.utils;

import java.util.zip.CRC32;

public abstract class CRC32Helper {
  public static long genCRCValue(byte[] buffer) {
    if(buffer.length < 5)
      throw new IllegalArgumentException("The length of the buffer must to be bigger of 4.");

    CRC32 crc32 = new CRC32();
    crc32.update(buffer);
    return crc32.getValue();
  }

  public static byte[] genCRCBytes(byte[] buffer) {
    long crc32Value = genCRCValue(buffer);

    byte[] completeBuffer = new byte[4];

    completeBuffer[0] = (byte) (crc32Value & 0x000000ff);
    completeBuffer[1] = (byte) ((crc32Value & 0x0000ff00) >> 8);
    completeBuffer[2] = (byte) ((crc32Value & 0x00ff0000) >> 16);
    completeBuffer[3] = (byte) ((crc32Value & 0xff000000) >> 24);

    return completeBuffer;
  }

  public static byte[] genBuffer(byte[] buffer) {
    byte[] crcBytes = genCRCBytes(buffer);
    byte[] completeBuffer = new byte[buffer.length + 4];

    System.arraycopy(buffer, 0, completeBuffer, 0, buffer.length);
    System.arraycopy(crcBytes, 0, completeBuffer, buffer.length + 1, completeBuffer.length);

    return completeBuffer;
  }
}
