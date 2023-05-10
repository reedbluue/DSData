package dev.ioliver.utils;

/**
 * The type Scale helper.
 */
public abstract class ScaleHelper {
  /**
   * Scale double.
   *
   * @param valor the valor
   * @param iMin  the min
   * @param iMax  the max
   * @param oMin  the o min
   * @param oMax  the o max
   * @return the double result
   */
  public static double scale(double valor, double iMin, double iMax, double oMin, double oMax) {
    double scale = (valor - iMin) / (iMax - iMin);
    return oMin + scale * (oMax - oMin);
  }
}
