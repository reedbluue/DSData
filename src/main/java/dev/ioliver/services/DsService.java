package dev.ioliver.services;

import org.hid4java.HidDevice;
import org.hid4java.HidManager;
import org.hid4java.HidServices;
import org.hid4java.HidServicesListener;
import org.hid4java.event.HidServicesEvent;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DsService {

  private static final HidServices hidService = HidManager.getHidServices();
  private final String DEVICE_NAME = "Wireless Controller";
  HidServicesListener listener = new HidServicesListener() {
    @Override
    public void hidDeviceAttached(HidServicesEvent hidServicesEvent) {
      if (hidServicesEvent.getHidDevice().getProduct().equals(DEVICE_NAME))
        System.out.println(hidServicesEvent.getHidDevice().getProduct() + " [" + hidServicesEvent.getHidDevice().getSerialNumber() + "] " + "pareado com sucesso!");
    }

    @Override
    public void hidDeviceDetached(HidServicesEvent hidServicesEvent) {
      if (hidServicesEvent.getHidDevice().getProduct().equals(DEVICE_NAME))
        System.out.println(hidServicesEvent.getHidDevice().getProduct() + " [" + hidServicesEvent.getHidDevice().getSerialNumber() + "] " + "despareado com sucesso!");
    }

    @Override
    public void hidFailure(HidServicesEvent hidServicesEvent) {
      System.out.println(hidServicesEvent.getHidDevice().getProduct() + " [" + hidServicesEvent.getHidDevice().getSerialNumber() + "], " + "falha na conexão!");
    }
  };

  {
    Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
      System.out.println("Alive!");
    }, 0, 1, TimeUnit.MINUTES);

    hidService.addHidServicesListener(listener);
  }

  public List<HidDevice> getDevices() {
    List<HidDevice> devices = hidService.getAttachedHidDevices().stream().filter(d -> {
      String product = d.getProduct();
      return product != null && d.getProduct().equals(DEVICE_NAME);
    }).toList();
    return devices;
  }
}
