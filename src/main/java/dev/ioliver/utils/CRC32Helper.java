package dev.ioliver.utils;

import java.util.zip.CRC32;

public abstract class CRC32Helper {
  public static byte[] genBuffer(byte[] buffer) {
    CRC32 crc32 = new CRC32();
    crc32.update(buffer);
    long crc32Value = crc32.getValue();

    byte[] completeBuffer = new byte[buffer.length + 4];

    System.arraycopy(buffer, 0, completeBuffer, 0, buffer.length);

    completeBuffer[75] = (byte) (crc32Value & 0x000000ff);
    completeBuffer[76] = (byte) ((crc32Value & 0x0000ff00) >> 8);
    completeBuffer[77] = (byte) ((crc32Value & 0x00ff0000) >> 16);
    completeBuffer[78] = (byte) ((crc32Value & 0xff000000) >> 24);

    return completeBuffer;
  }
}
