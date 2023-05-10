import dev.ioliver.models.DsDevice;
import dev.ioliver.models.Report.InputReport;
import dev.ioliver.models.Report.OutputReportFCR;
import dev.ioliver.services.DsManager;

public class DsManagerTest {
  public static void main(String[] args) throws InterruptedException {

    DsManager manager = DsManager.getInstance();// Recupera uma instancia do DsManager

    while (true) {
      if (!manager.getConnectedDevices().isEmpty()) // Espera ao menos um controle estar pareado com o computador
      {
        DsDevice controller = manager.getConnectedDevices().stream().findFirst().orElseThrow(); // Retorna o primeiro controle encontrado

        InputReport inputReport = controller.getInputReport(); // Recupera o relatorio de entrada do controle

        OutputReportFCR outputReport = controller.getOutputReport();// Recupera o relatorio de saída do controle

        if (inputReport.getCross()) // Leitura do botão X do controle
        {
          outputReport.setLed(255, 0, 0); // caso o X seja pressionado, altera a cor do led para vermelho
        } else {
          outputReport.setReset(); // se não, envia os valores padrões da biblioteca para o controle
        }
      }
      Thread.sleep(5);
    }
  }
}
