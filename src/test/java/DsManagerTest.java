import dev.ioliver.services.DsManager;

public class DsManagerTest {
  public static void main(String[] args) throws InterruptedException {
    DsManager manager = new DsManager();

    while (true) {
      if(manager.connectedDevices.size() >= 1) {
        if(manager.connectedDevices.get(0).inputReport.getCross()) {
          manager.connectedDevices.get(0).outputReport.setLed(255, 0, 0);
          manager.connectedDevices.get(0).outputReport.setRumble(255, 255);
          manager.connectedDevices.get(0).outputReport.setFlash(128, 128);
        } else {
          manager.connectedDevices.get(0).outputReport.setReset();
        }
      }
      Thread.sleep(5);
    }
  }
}
