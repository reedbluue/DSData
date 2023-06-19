package dev.ioliver.models.Report;

import dev.ioliver.utils.ScaleHelper;

import java.math.BigInteger;

/**
 * The type Input report.
 *
 * @author Igor Oliveira
 */
public class InputReport extends Report {

  /**
   * Instantiates a new Input report.
   *
   * @param buffer the bytes buffer of data read from the controller
   */
  public InputReport(byte[] buffer) {
    super(buffer);
    if (this.getProtocol() != (byte) 0x11) {
      System.out.println("The protocol 0x" + String.format("%02X", getProtocol()) + " is not compatible!");
      this.resetData();
    }
  }

  /**
   * Gets left stick x-axis.
   *
   * @return the left stick x-axis (-255 - 255)
   */
  public int getLeftStickX() {
    if (this.getProtocol() != (byte) 0x11) return 0;
    return (int) ScaleHelper.scale(Byte.toUnsignedInt(this.getData()[2]), 0, 255, -100, 100);
  }

  /**
   * Gets left stick y-axis.
   *
   * @return the left stick y-axis (-255 - 255)
   */
  public int getLeftStickY() {
    if (this.getProtocol() != (byte) 0x11) return 0;
    return (int) ScaleHelper.scale(Byte.toUnsignedInt(this.getData()[3]), 0, 255, -100, 100) * -1;
  }

  /**
   * Gets right stick x-axis.
   *
   * @return the right stick x-axis (-255 - 255)
   */
  public int getRightStickX() {
    if (this.getProtocol() != (byte) 0x11) return 0;
    return (int) ScaleHelper.scale(Byte.toUnsignedInt(this.getData()[4]), 0, 255, -100, 100);
  }

  /**
   * Gets right stick y-axis.
   *
   * @return the right stick y-axis (-255 - 255)
   */
  public int getRightStickY() {
    if (this.getProtocol() != (byte) 0x11) return 0;
    return (int) ScaleHelper.scale(Byte.toUnsignedInt(this.getData()[5]), 0, 255, -100, 100) * -1;
  }

  /**
   * Gets triangle button.
   *
   * @return true if the triangle button
   */
  public boolean getTriangle() {
    return BigInteger.valueOf(this.getData()[6]).testBit(7);
  }

  /**
   * Gets circle button.
   *
   * @return true if the circle button
   */
  public boolean getCircle() {
    return BigInteger.valueOf(this.getData()[6]).testBit(6);
  }

  /**
   * Gets cross button.
   *
   * @return true if the cross button
   */
  public boolean getCross() {
    return BigInteger.valueOf(this.getData()[6]).testBit(5);
  }

  /**
   * Gets square button.
   *
   * @return true if the square button
   */
  public boolean getSquare() {
    return BigInteger.valueOf(this.getData()[6]).testBit(4);
  }

  /**
   * Gets d-pad in north direction.
   *
   * @return true if the d-pad in north direction
   */
  public boolean getDPadN() {
    return (this.getData()[6] & 0b00001111) == 0;
  }

  /**
   * Gets d-pad in north-east direction.
   *
   * @return true if the d-pad in north-east direction
   */
  public boolean getDPadNE() {
    return (this.getData()[6] & 0b00001111) == 1;
  }

  /**
   * Gets d-pad in east direction.
   *
   * @return true if the d-pad in east direction
   */
  public boolean getDPadE() {
    return (this.getData()[6] & 0b00001111) == 2;
  }

  /**
   * Gets d-pad in south-east direction.
   *
   * @return true if the d-pad in south-east direction
   */
  public boolean getDPadSE() {
    return (this.getData()[6] & 0b00001111) == 3;
  }

  /**
   * Gets d-pad in south direction.
   *
   * @return true if the d-pad in south direction
   */
  public boolean getDPadS() {
    return (this.getData()[6] & 0b00001111) == 4;
  }

  /**
   * Gets d-pad in south-west direction.
   *
   * @return true if the d-pad in south-west direction
   */
  public boolean getDPadSW() {
    return (this.getData()[6] & 0b00001111) == 5;
  }

  /**
   * Gets d-pad in west direction.
   *
   * @return true if the d-pad in west direction
   */
  public boolean getDPadW() {
    return (this.getData()[6] & 0b00001111) == 6;
  }

  /**
   * Gets d-pad in north-west direction.
   *
   * @return true if the d-pad in north-west direction
   */
  public boolean getDPadNW() {
    return (this.getData()[6] & 0b00001111) == 7;
  }

  /**
   * Gets R3.
   *
   * @return true if the R3 is pressed
   */
  public boolean getR3() {
    return BigInteger.valueOf(this.getData()[7]).testBit(7);
  }

  /**
   * Gets L3.
   *
   * @return true if the l3 is pressed
   */
  public boolean getL3() {
    return BigInteger.valueOf(this.getData()[7]).testBit(6);
  }

  /**
   * Gets options.
   *
   * @return true if the options is pressed
   */
  public boolean getOptions() {
    return BigInteger.valueOf(this.getData()[7]).testBit(5);
  }

  /**
   * Gets share.
   *
   * @return true if the share is pressed
   */
  public boolean getShare() {
    return BigInteger.valueOf(this.getData()[7]).testBit(4);
  }

  /**
   * Gets R2.
   *
   * @return true if the R2 is pressed
   */
  public boolean getR2() {
    return BigInteger.valueOf(this.getData()[7]).testBit(3);
  }

  /**
   * Gets L2.
   *
   * @return true if the L2 is pressed
   */
  public boolean getL2() {
    return BigInteger.valueOf(this.getData()[7]).testBit(2);
  }

  /**
   * Gets R1.
   *
   * @return true if the R1 is pressed
   */
  public boolean getR1() {
    return BigInteger.valueOf(this.getData()[7]).testBit(1);
  }

  /**
   * Gets L1.
   *
   * @return true if the L1 is pressed
   */
  public boolean getL1() {
    return BigInteger.valueOf(this.getData()[7]).testBit(0);
  }

  /**
   * Gets t-pad.
   *
   * @return true if the t-pad is pressed
   */
  public boolean getTPad() {
    return BigInteger.valueOf(this.getData()[8]).testBit(1);
  }

  /**
   * Gets PS.
   *
   * @return true if the PS is pressed
   */
  public boolean getPs() {
    return BigInteger.valueOf(this.getData()[8]).testBit(0);
  }

  /**
   * Gets L2 value.
   *
   * @return the L2 value (0 - 100)
   */
  public int getL2Value() {
    return (int) ScaleHelper.scale(Byte.toUnsignedInt(this.getData()[9]), 0, 255, 0, 100);
  }

  /**
   * Gets R2 value. (0 - 100)
   *
   * @return the R2 value (0 - 100)
   */
  public int getR2Value() {
    return (int) ScaleHelper.scale(Byte.toUnsignedInt(this.getData()[10]), 0, 255, 0, 100);
  }

  /**
   * Gets battery level.
   *
   * @return the battery level (0 - 100)
   */
  public int getBattery() {
    if (this.getData()[13] == 0) return 0;
    return (int) ScaleHelper.scale(Byte.toUnsignedInt(this.getData()[13]), 0, 18, 0, 100);
  }
}
