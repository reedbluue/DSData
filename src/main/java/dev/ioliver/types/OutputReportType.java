package dev.ioliver.types;

public enum OutputReportType {
  RUMBLE(0),
  COLOR(1),
  FLASH(2),
  ALL(3);

  public final int value;

  OutputReportType(int value) {
    this.value = value;
  }
}
