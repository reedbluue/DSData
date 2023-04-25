import dev.ioliver.models.DsDevice;
import dev.ioliver.services.DsService;

public class MainTest {
  public static void main(String[] args) throws InterruptedException {
    DsDevice device = DsService.getDevice("48:18:8d:2e:4a:56");

    while (true) {
      if(device.getCross()) {
        device.setLed((byte) 255, (byte) 0, (byte) 0);
        device.setFlash((byte) 150, (byte) 150);
        device.setRumble((byte) 255, (byte) 255);
      } else {
        device.setReset();
      }

      Thread.sleep(10);
    }
  }
}