package com.simicaleksandar.radar.adapter.delegates;

import android.app.Activity;

import com.simicaleksandar.radar.R;
import com.simicaleksandar.radar.adapter.viewholders.DogViewHolder;
import com.simicaleksandar.radar.datamodel.DogDataModel;
import com.simicaleksandar.radar.inner.RecyclerViewAdapterDelegate;

import radar.RadarViewHolder;

public class DogAdapterDelegate extends RecyclerViewAdapterDelegate<DogDataModel> {

  public DogAdapterDelegate(Activity activity) {
    super(activity, DogDataModel.class, R.layout.dog_desc);
  }

  @Override
  protected RadarViewHolder<DogDataModel> getViewHolderDelegate() {
    return new DogViewHolder();
  }
}
