package dev.ioliver.utils;

public abstract class ByteArrayHelper {
  public static byte[] objectToPrimitive(Byte[] arr) {
    byte[] conArr = new byte[arr.length];
    for(int i = 0; i < arr.length; i ++) {
      conArr[i] = arr[i];
    }
    return conArr;
  }
}
