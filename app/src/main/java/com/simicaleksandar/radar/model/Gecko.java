package com.simicaleksandar.radar.model;

/**
 * Created by Aleksandar on 4/30/2017.
 */

public class Gecko {

  String name;
  String eyeColor;

  public Gecko(String name, String eyeColor) {
    this.name = name;
    this.eyeColor = eyeColor;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEyeColor() {
    return eyeColor;
  }

  public void setEyeColor(String eyeColor) {
    this.eyeColor = eyeColor;
  }
}
