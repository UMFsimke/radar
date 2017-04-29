package com.simicaleksandar.radar;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class RadarViewHolder<T extends DisplayableItem> extends RecyclerView.ViewHolder {

  public RadarViewHolder(View itemView) {
    super(itemView);
    bindViews(itemView);
  }

  public abstract void bindViews(View itemView);

  public abstract void onBindViewHolder(@NonNull View itemView, int position, @Nullable T item);

}
