package dev.ioliver.types;

public enum ReportType {
  INPUT((byte) 0x01),
  OUTPUT((byte) 0x02),
  FEATURE((byte) 0x03),
  NULL((byte) 0x00);

  public final byte value;

  ReportType(byte value) {
    this.value = value;
  }
}
