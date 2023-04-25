package dev.ioliver.models;

import dev.ioliver.types.OutputReportType;
import dev.ioliver.utils.CRC32Helper;

public abstract class OutputReport {
  static byte[] led(int r, int g, int b) {
    byte[] buffer = genarateNewBuffer(OutputReportType.COLOR);

    buffer[9] = (byte) r; //r
    buffer[10] = (byte) g; //g
    buffer[11] = (byte) b; //b

    return buffer;
  }

  static byte[] rumble(byte weakValue, byte strongValue) {
    byte[] buffer = genarateNewBuffer(OutputReportType.RUMBLE);

    buffer[7] = weakValue;
    buffer[8] = strongValue;

    return buffer;
  }

  static byte[] flash(byte brightValue, byte darkValue) {
    byte[] buffer = genarateNewBuffer(OutputReportType.FLASH);

    buffer[12] = brightValue;
    buffer[13] = darkValue;

    return buffer;
  }

  static byte[] reset() {
    byte[] buffer = genarateNewBuffer(OutputReportType.ALL);

    buffer[9] = (byte) 255; //r
    buffer[10] = (byte) 255; //g
    buffer[11] = (byte) 255; //b
    buffer[7] = 0;
    buffer[8] = 0;
    buffer[12] = 0;
    buffer[13] = 0;

    return buffer;
  }

  private static byte[] genarateNewBuffer(OutputReportType type) {
    byte[] buffer = new byte[75];

    buffer[0] = (byte) 0xA2; // HID HEADER
    buffer[1] = (byte) 0x11; // HID Output Report ID
    buffer[2] = (byte) 0b10000000; // ENABLE

    switch (type.value) {
      case 0 -> buffer[4] = (byte) 0b11110001; // RUMBLE ENABLE
      case 1 -> buffer[4] = (byte) 0b11110010; // COLOR ENABLE
      case 2 -> buffer[4] = (byte) 0b11110100; // FLASH ENABLE
      case 3 -> buffer[4] = (byte) 0b11110111; // RESET ENABLE
    }

    return buffer;
  }

  public static byte[] removeHeaderOfBuffer(byte[] buffer) {
    if(buffer.length < 2 ) throw new RuntimeException("The buffer is too small!");
    byte[] modBuffer = new byte[buffer.length - 2];
    System.arraycopy(buffer, 2, modBuffer, 0, modBuffer.length);

    return modBuffer;
  }
}
