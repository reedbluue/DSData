package dev.ioliver.models;

import dev.ioliver.models.Report.InputReport;
import dev.ioliver.models.Report.OutputReportFCR;
import dev.ioliver.utils.ByteArrayHelper;
import org.hid4java.HidDevice;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * The type Ds device.
 *
 * @author Igor Oliveira
 */
public class DsDevice {

  private final OutputReportFCR outputReport = new OutputReportFCR();
  private final String name, path, serial, mac;
  private final int pid, vid;
  private final HidDevice hid;
  private InputReport inputReport;
  private boolean isAlive = false;
  private ScheduledFuture<?> keepAliveExecutor;

  {
    byte[] arr = new byte[75];
    Arrays.fill(arr, (byte) 0x00);
    inputReport = new InputReport(arr);
  }

  /**
   * Instantiates a new Ds device.
   *
   * @param hidDevice the hid device
   */
  public DsDevice(HidDevice hidDevice) {
    hid = hidDevice;
    name = hid.getProduct();
    path = hid.getPath();
    pid = hid.getProductId();
    vid = hid.getVendorId();
    mac = hid.getSerialNumber().toUpperCase();
    serial = hid.getSerialNumber();

    System.out.println("Device " + this + " !");

    keepAlive();
  }

  @Override
  public String toString() {
    return "[ " + name + ", " + mac + " instantiated!]";
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets path.
   *
   * @return the path
   */
  public String getPath() {
    return path;
  }

  /**
   * Gets serial.
   *
   * @return the serial
   */
  public String getSerial() {
    return serial;
  }

  /**
   * Gets pid.
   *
   * @return the pid
   */
  public int getPid() {
    return pid;
  }

  /**
   * Gets vid.
   *
   * @return the vid
   */
  public int getVid() {
    return vid;
  }

  /**
   * Gets mac.
   *
   * @return the mac
   */
  public String getMac() {
    return mac;
  }

  private void keepAlive() {
    keepAliveExecutor = Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
      try {
        if (!isAlive) {
          System.out.println(this + ": Trying to open a new connection...");
          if (!hid.open()) throw new RuntimeException(this + ": The controller cannot be started!");
          isAlive = true;
          System.out.println(this + ": Connection has been established!");
        }

        byte[] getReport = new byte[64];
        getReport[0] = 0x02;
        int resposeData = hid.getFeatureReport(getReport, (byte) 0x02);

        if (resposeData == -1) throw new RuntimeException(this + ": Bad communication with the controller.");

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
    }, 0, 5, TimeUnit.MILLISECONDS);
  }

  /**
   * Stop keep alive.
   */
  public void stopKeepAlive() {
    keepAliveExecutor.cancel(true);
  }

  private byte[] readInputData() {
    try {
      Byte[] res = hid.read(74);
      if (!(res instanceof Byte[])) {
        System.out.println(this + ": Bad data read!");
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

  /**
   * Gets output report.
   *
   * @return the output report
   */
  public OutputReportFCR getOutputReport() {
    return outputReport;
  }

  /**
   * Gets input report.
   *
   * @return the input report
   */
  public InputReport getInputReport() {
    return inputReport;
  }
}
