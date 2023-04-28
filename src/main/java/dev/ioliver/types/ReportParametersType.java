package dev.ioliver.types;

public enum ReportParametersType {
  PARAM_0X00((byte) 0x00),
  PARAM_0X01((byte) 0x01),
  PARAM_0X02((byte) 0x02);

  public final byte value;

  ReportParametersType(byte value) {
    this.value = value;
  }
}
