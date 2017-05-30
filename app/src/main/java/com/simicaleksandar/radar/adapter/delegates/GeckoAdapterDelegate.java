package com.simicaleksandar.radar.adapter.delegates;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import com.simicaleksandar.radar.R;
import com.simicaleksandar.radar.RadarViewHolder;
import com.simicaleksandar.radar.datamodel.GeckoDataModel;
import com.simicaleksandar.radar.inner.RecyclerViewAdapterDelegate;

public class GeckoAdapterDelegate extends RecyclerViewAdapterDelegate<GeckoDataModel> {

  public GeckoAdapterDelegate(Activity activity, Class<GeckoDataModel> clazz,
      @LayoutRes int layoutId) {
    super(activity, clazz, layoutId);
  }

  @Override
  protected GeckoRowViewHolder getViewHolder(View itemView) {
    return new GeckoRowViewHolder(itemView);
  }

  private static class GeckoRowViewHolder extends RadarViewHolder<GeckoDataModel> {

    TextView geckoDescription;

    public GeckoRowViewHolder(View itemView) {
      super(itemView);
    }

    @Override
    public void bindViews(View itemView) {
      geckoDescription = (TextView) itemView.findViewById(R.id.gecko_desc);
    }

    @Override
    public void onBindViewHolder(@NonNull View itemView, int position,
        @Nullable GeckoDataModel item) {
      geckoDescription.setText(item.getColorfulGeckoName());
    }
  }
}
