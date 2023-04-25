package dev.ioliver.models;

import dev.ioliver.utils.CRC32Helper;
import org.hid4java.HidDevice;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class DsDevice {

  private final String name, path, serial, mac;
  private final int pid, vid;
  private final HidDevice hid;
  private final byte[] finalOutputReport;
  private InputReport lastInputReport;
  private boolean isAlive = false;

  {
    Byte[] arr = new Byte[78];
    Arrays.fill(arr, (byte) 0x00);
    finalOutputReport = new byte[75];
    Arrays.fill(finalOutputReport, (byte) 0x00);
    lastInputReport = new InputReport(arr);
  }

  public DsDevice(HidDevice hidDevice) {
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

  public int getLeftStickX() {
    return lastInputReport.getLeftStickX();
  }

  public int getLeftStickY() {
    return lastInputReport.getLeftStickY();
  }

  public int getRightStickX() {
    return lastInputReport.getRightStickX();
  }

  public int getRightStickY() {
    return lastInputReport.getRightStickY();
  }

  public boolean getTriangle() {
    return lastInputReport.getTriangle();
  }

  public boolean getCircle() {
    return lastInputReport.getCircle();
  }

  public boolean getCross() {
    return lastInputReport.getCross();
  }

  public boolean getSquare() {
    return lastInputReport.getSquare();
  }

  public boolean getDPadN() {
    return lastInputReport.getDPadN();
  }

  public boolean getDPadNE() {
    return lastInputReport.getDPadNE();
  }

  public boolean getDPadE() {
    return lastInputReport.getDPadE();
  }

  public boolean getDPadSE() {
    return lastInputReport.getDPadSE();
  }

  public boolean getDPadS() {
    return lastInputReport.getDPadS();
  }

  public boolean getDPadSW() {
    return lastInputReport.getDPadSW();
  }

  public boolean getDPadW() {
    return lastInputReport.getDPadW();
  }

  public boolean getDPadNW() {
    return lastInputReport.getDPadNW();
  }

  public boolean getR3() {
    return lastInputReport.getR3();
  }

  public boolean getL3() {
    return lastInputReport.getL3();
  }

  public boolean getOptions() {
    return lastInputReport.getOptions();
  }

  public boolean getShare() {
    return lastInputReport.getShare();
  }

  public boolean getR2() {
    return lastInputReport.getR2();
  }

  public boolean getL2() {
    return lastInputReport.getL2();
  }

  public boolean getR1() {
    return lastInputReport.getR1();
  }

  public boolean getL1() {
    return lastInputReport.getL1();
  }

  public boolean getTPad() {
    return lastInputReport.getTPad();
  }

  public boolean getPs() {
    return lastInputReport.getPs();
  }

  public int getL2Value() {
    return lastInputReport.getL2Value();
  }

  public int getR2Value() {
    return lastInputReport.getR2Value();
  }

  public int getBattery() {
    return lastInputReport.getBattery();
  }

  public Byte[] getRawData() {
    return this.lastInputReport.getRawData();
  }

  public void setLed(byte r, byte g, byte b) {
    byte[] report = OutputReport.led(r, g, b);
    updateFinalOutputReport(report);
  }

  public void setRumble(byte weakValue, byte strongValue) {
    byte[] report = OutputReport.rumble(weakValue, strongValue);
    updateFinalOutputReport(report);
  }

  public void setFlash(byte brightValue, byte darkValue) {
    byte[] report = OutputReport.flash(brightValue, darkValue);
    updateFinalOutputReport(report);
  }

  public void setReset() {
    byte[] report = OutputReport.reset();
    updateFinalOutputReport(report);
  }

  private void updateFinalOutputReport(byte[] buffer) {
    byte[] tempBuffer = new byte[finalOutputReport.length];
    System.arraycopy(finalOutputReport, 0, tempBuffer, 0, tempBuffer.length);
    IntStream.range(0, tempBuffer.length).forEach(x -> tempBuffer[x] |= buffer[x]);
    IntStream.range(7, tempBuffer.length).forEach(x -> {
      if (buffer[x] != (byte) 0x00) tempBuffer[x] = buffer[x];
    });
    System.arraycopy(tempBuffer, 0, finalOutputReport, 0, finalOutputReport.length);
  }

  private void keepAlive() {
    Executors.newScheduledThreadPool(1).scheduleAtFixedRate(this::updateLastInputReport, 0, 2, TimeUnit.MILLISECONDS);
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
      } catch (Exception err) {
        hid.close();
        isAlive = false;
        System.out.println(err.getMessage());
      }
    }, 1000, 1000, TimeUnit.MILLISECONDS);
  }

  private void updateLastInputReport() {
    Byte[] blankArr = new Byte[78];
    Arrays.fill(blankArr, (byte) 0x00);
    try {
      var res = hid.read();

      if(res.length > 0) {
        try {
          if (res[0] == 0x11) lastInputReport = new InputReport(res);
          if (finalOutputReport[0] != 0x00) {
            byte[] buffer = OutputReport.removeHeaderOfBuffer(CRC32Helper.genBuffer(finalOutputReport));
            hid.write(buffer, buffer.length, (byte) 0x11);
            Arrays.fill(finalOutputReport, (byte) 0x00);
          }
        } catch (Exception err) {
          lastInputReport = new InputReport(blankArr);
          System.out.println(err.getMessage());
        }
      } else {
        lastInputReport = new InputReport(blankArr);
      }
    } catch (RuntimeException err) {
      lastInputReport = new InputReport(blankArr);
    }
  }
}
