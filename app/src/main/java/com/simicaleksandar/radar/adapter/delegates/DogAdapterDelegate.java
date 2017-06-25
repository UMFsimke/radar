package com.simicaleksandar.radar.adapter.delegates;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import com.simicaleksandar.radar.R;
import radar.RadarViewHolder;
import com.simicaleksandar.radar.datamodel.DogDataModel;
import com.simicaleksandar.radar.inner.RecyclerViewAdapterDelegate;

public class DogAdapterDelegate extends RecyclerViewAdapterDelegate<DogDataModel> {

  public DogAdapterDelegate(Activity activity, Class<DogDataModel> clazz,
      @LayoutRes int layoutId) {
    super(activity, clazz, layoutId);
  }

  @Override
  protected RadarViewHolder<DogDataModel> getViewHolderDelegate() {
    return new DogRowViewHolder();
  }

  private static class DogRowViewHolder implements RadarViewHolder<DogDataModel> {

    private TextView dogDescription;

    @Override
    public void onCreateViewHolder(View itemView) {
      dogDescription = (TextView) itemView.findViewById(R.id.dog_desc);
    }

    @Override
    public void onBindViewHolder(@NonNull View itemView, int position,
        @Nullable DogDataModel item) {
      dogDescription.setText(item.getColorfulDogRace());
    }
  }
}
