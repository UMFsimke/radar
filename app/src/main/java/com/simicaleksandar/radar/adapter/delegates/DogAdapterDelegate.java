package com.simicaleksandar.radar.adapter.delegates;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import com.simicaleksandar.radar.R;
import com.simicaleksandar.radar.RadarViewHolder;
import com.simicaleksandar.radar.datamodel.DogDataModel;
import com.simicaleksandar.radar.inner.RecyclerViewAdapterDelegate;

public class DogAdapterDelegate extends RecyclerViewAdapterDelegate<DogDataModel> {

  public DogAdapterDelegate(Activity activity, Class<DogDataModel> clazz,
      @LayoutRes int layoutId) {
    super(activity, clazz, layoutId);
  }

  @Override
  protected DogRowViewHolder getViewHolder(View itemView) {
    return new DogRowViewHolder(itemView);
  }

  private static class DogRowViewHolder extends RadarViewHolder<DogDataModel> {

    TextView dogDescription;

    public DogRowViewHolder(View itemView) {
      super(itemView);
    }

    @Override
    public void bindViews(View itemView) {
      dogDescription = (TextView) itemView.findViewById(R.id.dog_desc);
    }

    @Override
    public void onBindViewHolder(@NonNull View itemView, int position,
        @Nullable DogDataModel item) {
      dogDescription.setText(item.getColorfulDogRace());
    }
  }
}
