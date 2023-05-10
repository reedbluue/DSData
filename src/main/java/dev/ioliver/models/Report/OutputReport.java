package dev.ioliver.models.Report;

import dev.ioliver.types.ReportParametersType;
import dev.ioliver.types.ReportTransactionType;
import dev.ioliver.types.ReportType;

/**
 * The type Output report.
 *
 * @author Igor Oliveira
 */
public class OutputReport extends Report {

  /**
   * Instantiates a new Output report.
   *
   * @param protocol the protocol of report
   * @param dataSize the data size of report
   */
  public OutputReport(byte protocol, int dataSize) {
    super(protocol, dataSize);
  }

  /**
   * Sets data.
   *
   * @param data the data to send to device
   */
  public void setData(byte[] data) {
    this.data = data.clone();
  }

  /**
   * Sets header.
   *
   * @param reportType            the report type
   * @param reportParametersType  the report parameters type
   * @param reportTransactionType the report transaction type
   */
  public void setHeader(ReportType reportType, ReportParametersType reportParametersType, ReportTransactionType reportTransactionType) {
    header = new ReportPacketPayloadHeader(reportType, reportParametersType, reportTransactionType);
  }

  /**
   * Get send buffer byte [ ].
   *
   * @return the byte [ ] to send to device
   */
  public byte[] getSendBuffer() {
    byte[] crc = getDataIntegrityCheck();
    byte[] concatedReport = getConcatedReport();
    byte[] sendBuffer = new byte[concatedReport.length + crc.length - 2];

    System.arraycopy(concatedReport, 2, sendBuffer, 0, concatedReport.length - 2);
    System.arraycopy(crc, 0, sendBuffer, concatedReport.length - 2, crc.length);

    return sendBuffer;
  }
}
