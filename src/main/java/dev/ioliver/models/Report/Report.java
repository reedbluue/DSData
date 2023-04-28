package dev.ioliver.models.Report;

import dev.ioliver.types.ReportParametersType;
import dev.ioliver.types.ReportTransactionType;
import dev.ioliver.types.ReportType;
import dev.ioliver.utils.CRC32Helper;

import java.util.Arrays;

public class Report {
  protected ReportPacketPayloadHeader header;
  protected byte protocol;
  protected byte[] data;

  public Report(byte protocol, int dataSize) {
    header = new ReportPacketPayloadHeader(ReportType.INPUT, ReportParametersType.PARAM_0X00, ReportTransactionType.DATA);
    this.protocol = protocol;
    this.data = new byte[dataSize];
    Arrays.fill(this.data, (byte) 0x00);
  }

  public Report(byte[] buffer) {
    header = new ReportPacketPayloadHeader(buffer[0]);
    this.protocol = buffer[1];
    this.data = new byte[buffer.length - 2];
    System.arraycopy(buffer, 2, this.data, 0, buffer.length - 2);
  }

  public byte[] getData() {
    return data;
  }

  public byte getProtocol() {
    return protocol;
  }

  public ReportPacketPayloadHeader getHeader() {
    return this.header;
  }

  public long getDataIntegrityCheckValue() {
    return CRC32Helper.genCRCValue(getConcatedReport());
  }

  public byte[] getDataIntegrityCheck() {
    return CRC32Helper.genCRCBytes(getConcatedReport());
  }

  public byte[] getConcatedReport() {
    byte[] concatedReport = new byte[data.length + 2];

    concatedReport[0] = this.header.getByteValue();
    concatedReport[1] = this.protocol;
    System.arraycopy(data, 0, concatedReport, 2, data.length);

    return concatedReport;
  }

  public void resetData() {
    Arrays.fill(this.data, (byte) 0x00);
  }
}
