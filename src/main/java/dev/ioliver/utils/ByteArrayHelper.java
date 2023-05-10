package dev.ioliver.utils;

import java.util.stream.IntStream;

/**
 * The type Byte array helper.
 *
 * @author Igor Oliveira
 */
public abstract class ByteArrayHelper {
  /**
   * Object to primitive byte [ ].
   *
   * @param arr the array
   * @return the byte [ ]
   */
  public static byte[] objectToPrimitive(Byte[] arr) {
    byte[] conArr = new byte[arr.length];
    IntStream.range(0, arr.length).forEach(i -> conArr[i] = arr[i]);
    return conArr;
  }
}
