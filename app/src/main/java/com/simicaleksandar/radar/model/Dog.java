package com.simicaleksandar.radar.model;

/**
 * Created by Aleksandar on 4/30/2017.
 */

public class Dog {

  private String race;
  private String color;

  public Dog(String race, String color) {
    this.race = race;
    this.color = color;
  }

  public String getRace() {
    return race;
  }

  public void setRace(String race) {
    this.race = race;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }
}
