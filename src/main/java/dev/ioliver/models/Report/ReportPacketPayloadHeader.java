package dev.ioliver.models.Report;

import dev.ioliver.types.ReportParametersType;
import dev.ioliver.types.ReportTransactionType;
import dev.ioliver.types.ReportType;

import java.util.Arrays;

/**
 * The type Report packet payload header.
 *
 * @author Igor Oliveira
 */
public class ReportPacketPayloadHeader {
  private final ReportType reportType;
  private final ReportParametersType reportParametersType;
  private final ReportTransactionType reportTransactionType;

  /**
   * Instantiates a new Report packet payload header.
   *
   * @param reportType            the report type
   * @param reportParametersType  the report parameters type
   * @param reportTransactionType the report transaction type
   */
  public ReportPacketPayloadHeader(ReportType reportType, ReportParametersType reportParametersType, ReportTransactionType reportTransactionType) {
    this.reportType = reportType;
    this.reportParametersType = reportParametersType;
    this.reportTransactionType = reportTransactionType;
  }

  /**
   * Instantiates a new Report packet payload header.
   *
   * @param headerByte the header byte
   */
  public ReportPacketPayloadHeader(Byte headerByte) {
    byte reportTypeValue = (byte) (headerByte & 0b00000011);
    byte parametersTypeValue = (byte) ((headerByte & 0b00001100) >> 2);
    byte trasactionTypeValue = (byte) ((headerByte & 0b11110000) >> 4);

    this.reportType = Arrays.stream(ReportType.values()).toList().stream().filter(type -> type.value == reportTypeValue).findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid ReportType on Report header! (" + reportTypeValue + ")"));
    this.reportParametersType = Arrays.stream(ReportParametersType.values()).toList().stream().filter(type -> type.value == parametersTypeValue).findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid ReportParametersType on Report header! (" + parametersTypeValue + ")"));
    this.reportTransactionType = Arrays.stream(ReportTransactionType.values()).toList().stream().filter(type -> type.value == trasactionTypeValue).findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid ReportTransactionType on Report header! (" + trasactionTypeValue + ")"));
  }

  /**
   * Gets report type.
   *
   * @return the report type
   */
  public ReportType getReportType() {
    return reportType;
  }

  /**
   * Gets report parameters type.
   *
   * @return the report parameters type
   */
  public ReportParametersType getReportParametersType() {
    return reportParametersType;
  }

  /**
   * Gets report transaction type.
   *
   * @return the report transaction type
   */
  public ReportTransactionType getReportTransactionType() {
    return reportTransactionType;
  }

  /**
   * Gets byte value.
   *
   * @return the byte value
   */
  public byte getByteValue() {
    return (byte) ((reportTransactionType.value << 4) | (reportParametersType.value << 2) | reportType.value);
  }
}
