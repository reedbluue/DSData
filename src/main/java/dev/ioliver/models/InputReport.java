package dev.ioliver.models;

import dev.ioliver.utils.ScaleHelper;

import java.math.BigInteger;

public class InputReport {
  private final Byte[] data;

  public InputReport(Byte[] data) {
    this.data = data;
  }

  public Byte[] getRawData() {
    return this.data;
  }

  public int getLeftStickX() {
    return (int) ScaleHelper.scale(Byte.toUnsignedInt(data[3]), 0, 255, -255, 255);
  }

  public int getLeftStickY() {
    return (int) ScaleHelper.scale(Byte.toUnsignedInt(data[4]), 0, 255, -255, 255) * -1;
  }

  public int getRightStickX() {
    return (int) ScaleHelper.scale(Byte.toUnsignedInt(data[5]), 0, 255, -255, 255);
  }

  public int getRightStickY() {
    return (int) ScaleHelper.scale(Byte.toUnsignedInt(data[6]), 0, 255, -255, 255) * -1;
  }

  public boolean getTriangle() {
    return BigInteger.valueOf(data[7]).testBit(7);
  }

  public boolean getCircle() {
    return BigInteger.valueOf(data[7]).testBit(6);
  }

  public boolean getCross() {
    return BigInteger.valueOf(data[7]).testBit(5);
  }

  public boolean getSquare() {
    return BigInteger.valueOf(data[7]).testBit(4);
  }

  public boolean getDPadN() {
    return (data[7] & 0b00001111) == 0;
  }

  public boolean getDPadNE() {
    return (data[7] & 0b00001111) == 1;
  }

  public boolean getDPadE() {
    return (data[7] & 0b00001111) == 2;
  }

  public boolean getDPadSE() {
    return (data[7] & 0b00001111) == 3;
  }

  public boolean getDPadS() {
    return (data[7] & 0b00001111) == 4;
  }

  public boolean getDPadSW() {
    return (data[7] & 0b00001111) == 5;
  }

  public boolean getDPadW() {
    return (data[7] & 0b00001111) == 6;
  }

  public boolean getDPadNW() {
    return (data[7] & 0b00001111) == 7;
  }

  public boolean getR3() {
    return BigInteger.valueOf(data[8]).testBit(7);
  }

  public boolean getL3() {
    return BigInteger.valueOf(data[8]).testBit(6);
  }

  public boolean getOptions() {
    return BigInteger.valueOf(data[8]).testBit(5);
  }

  public boolean getShare() {
    return BigInteger.valueOf(data[8]).testBit(4);
  }

  public boolean getR2() {
    return BigInteger.valueOf(data[8]).testBit(3);
  }

  public boolean getL2() {
    return BigInteger.valueOf(data[8]).testBit(2);
  }

  public boolean getR1() {
    return BigInteger.valueOf(data[8]).testBit(1);
  }

  public boolean getL1() {
    return BigInteger.valueOf(data[8]).testBit(0);
  }

  public boolean getTPad() {
    return BigInteger.valueOf(data[9]).testBit(1);
  }

  public boolean getPs() {
    return BigInteger.valueOf(data[9]).testBit(0);
  }

  public int getL2Value() {
    return (int) ScaleHelper.scale(Byte.toUnsignedInt(data[10]), 0, 255, 0, 100);
  }

  public int getR2Value() {
    return (int) ScaleHelper.scale(Byte.toUnsignedInt(data[11]), 0, 255, 0, 100);
  }

  public int getBattery() {
    return (int) ScaleHelper.scale(Math.abs(255 - Byte.toUnsignedInt(data[14])), 0, 255, 0, 100);
  }

}
