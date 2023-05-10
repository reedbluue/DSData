import dev.ioliver.models.Report.OutputReport;
import dev.ioliver.models.Report.OutputReportFCR;
import dev.ioliver.models.Report.Report;

import java.util.Arrays;

/**
 * The type Report test.
 */
public class ReportTest {
  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    OutputReportFCR output = new OutputReportFCR();
    output.setLed(255, 10, 100);

    System.out.println(output.getProtocol());
    System.out.println(Byte.toUnsignedInt(output.getHeader().getByteValue()));
    System.out.println(Arrays.toString(output.getSendBuffer()));

    byte[] bytes = new byte[64];
    Arrays.fill(bytes, (byte) 0x00);

    Report x = new Report(bytes);
    System.out.println(x.getHeader().getByteValue());
  }
}
