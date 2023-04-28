package dev.ioliver.models;

import dev.ioliver.models.Report.InputReport;
import dev.ioliver.models.Report.OutputReportFCR;
import dev.ioliver.utils.ByteArrayHelper;
import dev.ioliver.utils.CRC32Helper;
import org.hid4java.HidDevice;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DsDevice {

  private final String name, path, serial, mac;
  private final int pid, vid;
  private final HidDevice hid;
  public final OutputReportFCR outputReport = new OutputReportFCR();
  public InputReport inputReport;
  private boolean isAlive = false;

  {
    byte[] arr = new byte[75];
    Arrays.fill(arr, (byte) 0x00);
    inputReport = new InputReport(arr);
  }

  public DsDevice(HidDevice hidDevice) {
    System.out.println("Instaciado.");
    hid = hidDevice;
    name = hid.getProduct();
    path = hid.getPath();
    pid = hid.getProductId();
    vid = hid.getVendorId();
    mac = hid.getSerialNumber();
    serial = hid.getSerialNumber();
    keepAlive();
  }

  public String getName() {
    return name;
  }

  public String getPath() {
    return path;
  }

  public String getSerial() {
    return serial;
  }

  public int getPid() {
    return pid;
  }

  public int getVid() {
    return vid;
  }

  public String getMac() {
    return mac;
  }

  private void keepAlive() {
    Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
      try {
        if (!isAlive) {
          System.out.println("Trying to open a new connection...");
          if (!hid.open()) throw new RuntimeException("The controller cannot be started!");
          isAlive = true;
          System.out.println("Connection has been established!");
        }

        byte[] getReport = new byte[64];
        getReport[0] = 0x02;
        int resposeData = hid.getFeatureReport(getReport, (byte) 0x02);

        if (resposeData == -1) throw new RuntimeException("Bad communication with the controller.");

        updateInputReport();
        updateOutputReport();
      } catch (Exception err) {
        hid.close();
        inputReport.resetData();
        isAlive = false;
        System.out.println(err.getMessage());
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          System.out.println(e.getMessage());
        }
      }
    }, 0, 1, TimeUnit.MILLISECONDS);
  }

  private byte[] readInputData() {
    try {
      Byte[] res = hid.read(74);
      if(!(res instanceof Byte[])) {
        System.out.println("Falha na leitura dos dados!");
        return new byte[0];
      }
      return ByteArrayHelper.objectToPrimitive(res);
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      return new byte[0];
    }
  }

  private void updateInputReport() {
    byte[] buffer = readInputData();
    byte[] newBuffer = new byte[buffer.length + 1];
    newBuffer[0] = (byte) 0b10100001;
    System.arraycopy(buffer, 0, newBuffer, 1, buffer.length);
    this.inputReport = new InputReport(newBuffer);
  }

  private void updateOutputReport() {
    byte[] sendBuffer = outputReport.getSendBuffer();
    hid.write(sendBuffer, sendBuffer.length, outputReport.getProtocol());
    outputReport.resetReport();
  }
}
