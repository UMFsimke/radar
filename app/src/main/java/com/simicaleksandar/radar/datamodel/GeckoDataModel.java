package com.simicaleksandar.radar.datamodel;

import com.simicaleksandar.radar.DisplayableItem;
import com.simicaleksandar.radar.model.Gecko;

/**
 * Created by Aleksandar on 4/30/2017.
 */

public class GeckoDataModel implements DisplayableItem {

  private String colorfulGeckoName;

  public static GeckoDataModel newInstance(Gecko gecko) {
    GeckoDataModel dataModel = new GeckoDataModel();
    if (gecko.getEyeColor() == null) {
      dataModel.colorfulGeckoName = "Just colorless Gecko " + gecko.getName();
      return dataModel;
    }

    dataModel.colorfulGeckoName = gecko.getEyeColor() + "-eyed Gecko is called " + gecko.getName();
    return dataModel;
  }

  private GeckoDataModel() { }

  public String getColorfulGeckoName() {
    return colorfulGeckoName;
  }
}
