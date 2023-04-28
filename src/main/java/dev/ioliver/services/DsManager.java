package dev.ioliver.services;

import dev.ioliver.models.DsDevice;
import org.hid4java.HidDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DsManager {
  private final DsService service = new DsService();
  public ArrayList<DsDevice> connectedDevices = new ArrayList<>();

  public DsManager() {
    Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
      try {
        List<HidDevice> devices = service.getDevices();
        for(HidDevice d : devices) {
          if(!checkIfDeviceExists(d)) {
            DsDevice dsDevice = new DsDevice(d);
            connectedDevices.add(dsDevice);
          }
        }
      } catch (RuntimeException e) {
        System.out.println(e.getMessage());
      }
    }, 1000, 5000, TimeUnit.MILLISECONDS);
  }

  private boolean checkIfDeviceExists(HidDevice hid) {
    String mac = hid.getSerialNumber();
    for(DsDevice d : connectedDevices) {
      if(d.getMac().equals(mac))
        return true;
    }
    return false;
  }
}
