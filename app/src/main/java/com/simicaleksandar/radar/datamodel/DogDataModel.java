package com.simicaleksandar.radar.datamodel;

import com.simicaleksandar.radar.model.Dog;

import radar.DisplayableItem;

public class DogDataModel implements DisplayableItem {

  private String colorfulDogRace;

  public static DogDataModel newInstance(Dog dog) {
    DogDataModel dataModel = new DogDataModel();
    dataModel.colorfulDogRace = dog.getColor() + " " + dog.getRace() + " is a dog";
    return dataModel;
  }

  private DogDataModel() { }

  public String getColorfulDogRace() {
    return colorfulDogRace;
  }
}
