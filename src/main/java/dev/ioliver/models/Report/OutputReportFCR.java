package dev.ioliver.models.Report;

import dev.ioliver.types.ReportParametersType;
import dev.ioliver.types.ReportTransactionType;
import dev.ioliver.types.ReportType;

/**
 * The type Output report FRC (Sends flash, rumble and color data ).
 *
 * @author Igor Oliveira
 */
public class OutputReportFCR extends OutputReport {

  /**
   * Instantiates a new Output report FRC.
   */
  public OutputReportFCR() {
    super((byte) 0x11, 73);
    setHeader(ReportType.OUTPUT, ReportParametersType.PARAM_0X00, ReportTransactionType.DATA);
    resetReport();
  }

  /**
   * Sets led.
   *
   * @param r the red level
   * @param g the green level
   * @param b the blue level
   */
  public void setLed(int r, int g, int b) {
    this.data[7] = (byte) r; //r
    this.data[8] = (byte) g; //g
    this.data[9] = (byte) b; //b
    this.data[2] = (byte) (this.data[2] | 0b11110010);
  }

  /**
   * Sets rumble.
   *
   * @param weakValue   the weak value
   * @param strongValue the strong value
   */
  public void setRumble(int weakValue, int strongValue) {
    this.data[5] = (byte) weakValue;
    this.data[6] = (byte) strongValue;
    this.data[2] = (byte) (this.data[2] | 0b11110001);
  }

  /**
   * Sets flash.
   *
   * @param brightValue the bright value
   * @param darkValue   the dark value
   */
  public void setFlash(int brightValue, int darkValue) {
    this.data[10] = (byte) brightValue;
    this.data[11] = (byte) darkValue;
    this.data[2] = (byte) (this.data[2] | 0b11110100);
  }

  /**
   * Reset report.
   */
  public void resetReport() {
    resetData();
    this.data[0] = (byte) 0b10000000;
    this.data[2] = (byte) 0b11110000;
  }

  /**
   * Sets reset. Sends default values to all properties.
   */
  public void setReset() {
    this.data[7] = (byte) 255; //r
    this.data[8] = (byte) 255; //g
    this.data[9] = (byte) 255; //b
    this.data[5] = 0;
    this.data[6] = 0;
    this.data[10] = 0;
    this.data[11] = 0;
    this.data[2] = (byte) (this.data[2] | 0b11110111);
  }
}
