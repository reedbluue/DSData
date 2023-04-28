package dev.ioliver.models.Report;

import dev.ioliver.utils.ScaleHelper;

import java.math.BigInteger;

public class InputReport extends Report{

  public InputReport(byte[] buffer) {
    super(buffer);
    if(this.getProtocol() != (byte) 0x11) {
      System.out.println("The protocol 0x" + String.format("%02X", getProtocol()) + " is not compatible!");
      this.resetData();
    }
  }

  public int getLeftStickX() {
    return (int) ScaleHelper.scale(Byte.toUnsignedInt(this.getData()[2]), 0, 255, -255, 255);}

  public int getLeftStickY() {
    return (int) ScaleHelper.scale(Byte.toUnsignedInt(this.getData()[3]), 0, 255, -255, 255) * -1;
  }

  public int getRightStickX() {
    return (int) ScaleHelper.scale(Byte.toUnsignedInt(this.getData()[4]), 0, 255, -255, 255);
  }

  public int getRightStickY() {
    return (int) ScaleHelper.scale(Byte.toUnsignedInt(this.getData()[5]), 0, 255, -255, 255) * -1;
  }

  public boolean getTriangle() {
    return BigInteger.valueOf(this.getData()[6]).testBit(7);
  }

  public boolean getCircle() {
    return BigInteger.valueOf(this.getData()[6]).testBit(6);
  }

  public boolean getCross() {
    return BigInteger.valueOf(this.getData()[6]).testBit(5);
  }

  public boolean getSquare() {
    return BigInteger.valueOf(this.getData()[6]).testBit(4);
  }

  public boolean getDPadN() {
    return (this.getData()[6] & 0b00001111) == 0;
  }

  public boolean getDPadNE() {
    return (this.getData()[6] & 0b00001111) == 1;
  }

  public boolean getDPadE() {
    return (this.getData()[6] & 0b00001111) == 2;
  }

  public boolean getDPadSE() {
    return (this.getData()[6] & 0b00001111) == 3;
  }

  public boolean getDPadS() {
    return (this.getData()[6] & 0b00001111) == 4;
  }

  public boolean getDPadSW() {
    return (this.getData()[6] & 0b00001111) == 5;
  }

  public boolean getDPadW() {
    return (this.getData()[6] & 0b00001111) == 6;
  }

  public boolean getDPadNW() {
    return (this.getData()[6] & 0b00001111) == 7;
  }

  public boolean getR3() {
    return BigInteger.valueOf(this.getData()[7]).testBit(7);
  }

  public boolean getL3() {
    return BigInteger.valueOf(this.getData()[7]).testBit(6);
  }

  public boolean getOptions() {
    return BigInteger.valueOf(this.getData()[7]).testBit(5);
  }

  public boolean getShare() {
    return BigInteger.valueOf(this.getData()[7]).testBit(4);
  }

  public boolean getR2() {
    return BigInteger.valueOf(this.getData()[7]).testBit(3);
  }

  public boolean getL2() {
    return BigInteger.valueOf(this.getData()[7]).testBit(2);
  }

  public boolean getR1() {
    return BigInteger.valueOf(this.getData()[7]).testBit(1);
  }

  public boolean getL1() {
    return BigInteger.valueOf(this.getData()[7]).testBit(0);
  }

  public boolean getTPad() {
    return BigInteger.valueOf(this.getData()[8]).testBit(1);
  }

  public boolean getPs() {
    return BigInteger.valueOf(this.getData()[8]).testBit(0);
  }

  public int getL2Value() {
    return (int) ScaleHelper.scale(Byte.toUnsignedInt(this.getData()[9]), 0, 255, 0, 100);
  }

  public int getR2Value() {
    return (int) ScaleHelper.scale(Byte.toUnsignedInt(this.getData()[10]), 0, 255, 0, 100);
  }

  public int getBattery() {
    if(this.getData()[13] == 0) return 0;
    return (int) ScaleHelper.scale(Math.abs(255 - Byte.toUnsignedInt(this.getData()[13])), 0, 255, 0, 100);
  }
}
