package com.simicaleksandar.radar.adapter.delegates;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import com.simicaleksandar.radar.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import radar.RadarViewHolder;
import com.simicaleksandar.radar.datamodel.GeckoDataModel;
import com.simicaleksandar.radar.inner.RecyclerViewAdapterDelegate;

public class GeckoAdapterDelegate extends RecyclerViewAdapterDelegate<GeckoDataModel> {

  public GeckoAdapterDelegate(Activity activity, Class<GeckoDataModel> clazz,
      @LayoutRes int layoutId) {
    super(activity, clazz, layoutId);
  }

  @Override
  protected GeckoRowViewHolder getViewHolderDelegate() {
    return new GeckoRowViewHolder();
  }

  protected static class GeckoRowViewHolder implements RadarViewHolder<GeckoDataModel> {

    @BindView(R.id.gecko_desc) TextView geckoDescription;

    @Override
    public void onCreateViewHolder(View itemView) {
      ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull View itemView, int position,
        @Nullable GeckoDataModel item) {
      geckoDescription.setText(item.getColorfulGeckoName());
    }
  }
}
