package dev.ioliver.services;

import dev.ioliver.models.DsDevice;
import org.hid4java.HidDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The type Ds manager.
 *
 * @author Igor Oliveira
 */
public class DsManager {
  private final DsService service = new DsService();
  private final ArrayList<DsDevice> connectedDevices = new ArrayList<>();

  /**
   * Instantiates a new Ds manager.
   */
  public DsManager() {
    Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
      try {
        List<HidDevice> devices = service.getDevices();
        connectedDevices.removeIf(cd -> {
          if (devices.stream().noneMatch(d -> d.getSerialNumber().equals(cd.getMac()))) {
            cd.stopKeepAlive();
            System.out.println("Device: " + cd + " de-instantiated!");
            return true;
          }
          return false;
        });
        for (HidDevice d : devices) {
          if (connectedDevices.stream().noneMatch(cd -> cd.getMac().equals(d.getSerialNumber()))) {
            DsDevice dsDevice = new DsDevice(d);
            connectedDevices.add(dsDevice);
          }
        }
      } catch (RuntimeException e) {
        System.out.println(e.getMessage());
      }
    }, 1000, 3000, TimeUnit.MILLISECONDS);
  }

  /**
   * Gets connected devices.
   *
   * @return The connected devices
   */
  public ArrayList<DsDevice> getConnectedDevices() {
    return connectedDevices;
  }
}
