package dev.ioliver.services;

import org.hid4java.HidDevice;
import org.hid4java.HidManager;
import org.hid4java.HidServices;
import org.hid4java.HidServicesListener;
import org.hid4java.event.HidServicesEvent;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The type Ds service.
 *
 * @author Igor Oliveira
 */
public class DsService {

  private static final HidServices hidService = HidManager.getHidServices();
  private final String DEVICE_NAME = "Wireless Controller";
  /**
   * The HID Listener
   */
  HidServicesListener listener = new HidServicesListener() {
    @Override
    public void hidDeviceAttached(HidServicesEvent hidServicesEvent) {
      if (hidServicesEvent.getHidDevice().getProduct().equals(DEVICE_NAME))
        System.out.println(hidServicesEvent.getHidDevice().getProduct() + " [" + hidServicesEvent.getHidDevice().getSerialNumber().toUpperCase() + "] " + "success on pair!");
    }

    @Override
    public void hidDeviceDetached(HidServicesEvent hidServicesEvent) {
      if (hidServicesEvent.getHidDevice().getProduct().equals(DEVICE_NAME))
        System.out.println(hidServicesEvent.getHidDevice().getProduct() + " [" + hidServicesEvent.getHidDevice().getSerialNumber().toUpperCase() + "] " + "success on dispair!");
    }

    @Override
    public void hidFailure(HidServicesEvent hidServicesEvent) {
      System.out.println(hidServicesEvent.getHidDevice().getProduct() + " [" + hidServicesEvent.getHidDevice().getSerialNumber().toUpperCase() + "], " + "connection fault!");
    }
  };

  {
    hidService.addHidServicesListener(listener);
  }

  /**
   * Gets devices.
   *
   * @return the devices paired with the PC
   */
  public List<HidDevice> getDevices() {
    List<HidDevice> devices = hidService.getAttachedHidDevices().stream().filter(d -> {
      String product = d.getProduct();
      return product != null && d.getProduct().equals(DEVICE_NAME);
    }).toList();
    return devices;
  }
}
