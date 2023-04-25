package dev.ioliver.services;

import dev.ioliver.models.DsDevice;
import org.hid4java.HidDevice;
import org.hid4java.HidManager;
import org.hid4java.HidServices;

import java.util.List;

public abstract class DsService {

  private static final HidServices hidService = HidManager.getHidServices();

  private static List<HidDevice> getDevices() {
    return hidService.getAttachedHidDevices().stream().filter(device -> {
      return device.getProduct() != null && device.getProduct().equals("Wireless Controller");
    }).toList();
  }

  public static DsDevice getDevice(String mac) {
    String macValue = mac.replace(":", "");
    return DsService.getDevices().stream().filter(device -> device.getSerialNumber().equals(macValue))
        .map(DsDevice::new).findFirst().orElseThrow(
        () -> new RuntimeException("DS Controller not found with the MAC!"));
  }
}
