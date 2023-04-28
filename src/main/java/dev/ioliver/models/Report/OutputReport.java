package dev.ioliver.models.Report;

import dev.ioliver.types.ReportParametersType;
import dev.ioliver.types.ReportTransactionType;
import dev.ioliver.types.ReportType;

import java.util.Arrays;

public class OutputReport extends Report{

  public OutputReport(byte protocol, int dataSize) {
    super(protocol, dataSize);
  }

  public void setData(byte[] data) {
    this.data = data.clone();
  }

  public void setHeader(ReportType reportType, ReportParametersType reportParametersType, ReportTransactionType reportTransactionType) {
    header = new ReportPacketPayloadHeader(reportType, reportParametersType,reportTransactionType);
  }

  public byte[] getSendBuffer() {
    byte[] crc = getDataIntegrityCheck();
    byte[] concatedReport = getConcatedReport();
    byte[] sendBuffer = new byte[concatedReport.length + crc.length - 2];


    System.arraycopy(concatedReport, 2, sendBuffer, 0, concatedReport.length - 2);
    System.arraycopy(crc, 0, sendBuffer, concatedReport.length - 2, crc.length);

    return sendBuffer;
  }
}
