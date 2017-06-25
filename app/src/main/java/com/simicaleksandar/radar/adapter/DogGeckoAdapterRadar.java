package com.simicaleksandar.radar.adapter;

import android.app.Activity;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;
import com.simicaleksandar.radar.RadarBaseAdapter;
import com.simicaleksandar.radar.R;
import com.simicaleksandar.radar.adapter.delegates.DogAdapterDelegate;
import com.simicaleksandar.radar.adapter.delegates.GeckoAdapterDelegate;
import com.simicaleksandar.radar.datamodel.DogDataModel;
import com.simicaleksandar.radar.datamodel.GeckoDataModel;
import java.util.List;

import radar.DisplayableItem;

public class DogGeckoAdapterRadar extends RadarBaseAdapter {

  public DogGeckoAdapterRadar(Activity activity) {
    super(activity);
  }

  @Override
  protected void addAdapterDelegates(Activity activity,
      AdapterDelegatesManager<List<DisplayableItem>> manager) {
    manager.addDelegate(new DogAdapterDelegate(activity, DogDataModel.class,
        R.layout.dog_desc));
    manager.addDelegate(new GeckoAdapterDelegate(activity, GeckoDataModel.class,
        R.layout.gecko_desc));
  }
}