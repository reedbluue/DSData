package dev.ioliver.models.Report;

import dev.ioliver.types.ReportParametersType;
import dev.ioliver.types.ReportTransactionType;
import dev.ioliver.types.ReportType;

import java.util.Arrays;

public class ReportPacketPayloadHeader {
  private final ReportType reportType;
  private final ReportParametersType reportParametersType;
  private final ReportTransactionType reportTransactionType;

  public ReportPacketPayloadHeader(ReportType reportType, ReportParametersType reportParametersType, ReportTransactionType reportTransactionType) {
    this.reportType = reportType;
    this.reportParametersType = reportParametersType;
    this.reportTransactionType = reportTransactionType;
  }

  public ReportPacketPayloadHeader(Byte headerByte) {
    byte reportTypeValue = (byte) (headerByte & 0b00000011);
    byte parametersTypeValue = (byte) ((headerByte & 0b00001100) >> 2);
    byte trasactionTypeValue = (byte) ((headerByte & 0b11110000) >> 4);

    this.reportType = Arrays.stream(ReportType.values()).toList().stream().filter(type -> type.value == reportTypeValue)
        .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid ReportType on Report header! (" + reportTypeValue + ")"));
    this.reportParametersType = Arrays.stream(ReportParametersType.values()).toList().stream().filter(type -> type.value == parametersTypeValue)
        .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid ReportParametersType on Report header! (" + parametersTypeValue + ")"));
    this.reportTransactionType = Arrays.stream(ReportTransactionType.values()).toList().stream().filter(type -> type.value == trasactionTypeValue)
        .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid ReportTransactionType on Report header! (" + trasactionTypeValue + ")"));
  }

  public ReportType getReportType() {
    return reportType;
  }

  public ReportParametersType getReportParametersType() {
    return reportParametersType;
  }

  public ReportTransactionType getReportTransactionType() {
    return reportTransactionType;
  }

  public byte getByteValue() {
    return (byte) ((reportTransactionType.value << 4) | (reportParametersType.value << 2) | reportType.value);
  }
}
