package dev.ioliver.models.Report;

import dev.ioliver.types.ReportParametersType;
import dev.ioliver.types.ReportTransactionType;
import dev.ioliver.types.ReportType;
import dev.ioliver.utils.CRC32Helper;

import java.util.Arrays;

/**
 * The type Report.
 *
 * @author Igor Oliveira
 */
public class Report {
  /**
   * The Header of report.
   */
  protected ReportPacketPayloadHeader header;
  /**
   * The Protocol of report.
   */
  protected byte protocol;
  /**
   * The Data of report.
   */
  protected byte[] data;

  /**
   * Instantiates a new Report.
   *
   * @param protocol the protocol
   * @param dataSize the data size
   */
  public Report(byte protocol, int dataSize) {
    header = new ReportPacketPayloadHeader(ReportType.INPUT, ReportParametersType.PARAM_0X00, ReportTransactionType.DATA);
    this.protocol = protocol;
    this.data = new byte[dataSize];
    Arrays.fill(this.data, (byte) 0x00);
  }

  /**
   * Instantiates a new Report.
   *
   * @param buffer the buffer of data read from device
   */
  public Report(byte[] buffer) {
    header = new ReportPacketPayloadHeader(buffer[0]);
    this.protocol = buffer[1];
    this.data = new byte[buffer.length - 2];
    System.arraycopy(buffer, 2, this.data, 0, buffer.length - 2);
  }

  /**
   * Get data byte [ ].
   *
   * @return the byte [ ]
   */
  public byte[] getData() {
    return data;
  }

  /**
   * Gets protocol.
   *
   * @return the protocol
   */
  public byte getProtocol() {
    return protocol;
  }

  /**
   * Gets header.
   *
   * @return the header
   */
  public ReportPacketPayloadHeader getHeader() {
    return this.header;
  }

  /**
   * Gets data integrity check value.
   *
   * @return the data integrity check value
   */
  public long getDataIntegrityCheckValue() {
    return CRC32Helper.genCRCValue(getConcatedReport());
  }

  /**
   * Get data integrity check byte [ ].
   *
   * @return the byte [ ]
   */
  public byte[] getDataIntegrityCheck() {
    return CRC32Helper.genCRCBytes(getConcatedReport());
  }

  /**
   * Get concated report byte [ ].
   *
   * @return the byte [ ]
   */
  public byte[] getConcatedReport() {
    byte[] concatedReport = new byte[data.length + 2];

    concatedReport[0] = this.header.getByteValue();
    concatedReport[1] = this.protocol;
    System.arraycopy(data, 0, concatedReport, 2, data.length);

    return concatedReport;
  }

  /**
   * Reset data. Fill data with 0x00.
   */
  public void resetData() {
    Arrays.fill(this.data, (byte) 0x00);
  }
}
