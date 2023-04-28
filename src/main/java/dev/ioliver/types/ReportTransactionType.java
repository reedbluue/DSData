package dev.ioliver.types;

public enum ReportTransactionType {
  GET_REPORT((byte) 0x04),
  SET_REPORT((byte) 0x05),
  DATA((byte) 0x0A),
  NULL((byte) 0x00);

  public final byte value;

  ReportTransactionType(byte value) {
    this.value = value;
  }
}
