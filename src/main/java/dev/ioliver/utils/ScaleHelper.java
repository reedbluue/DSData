package dev.ioliver.utils;

public abstract class ScaleHelper {
  public static double scale(double valor, double iMin, double iMax, double oMin, double oMax) {
    double scale = (valor - iMin) / (iMax - iMin);
    return oMin + scale * (oMax - oMin);
  }
}
