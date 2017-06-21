package com.simicaleksandar.radar.inner;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.simicaleksandar.radar.BaseViewHolder;
import com.simicaleksandar.radar.DisplayableItem;
import com.simicaleksandar.radar.RadarViewHolder;

import java.util.List;

public abstract class RecyclerViewAdapterDelegate<T extends DisplayableItem> extends
    AdapterDelegate<List<DisplayableItem>> {

  private int layoutId;
  private Class<T> clazz;
  private LayoutInflaterManager layoutInflaterManager;

  public RecyclerViewAdapterDelegate(Activity activity, Class<T> clazz, @LayoutRes int layoutId) {
    layoutInflaterManager = LayoutInflaterManager.getInstance(activity);
    this.clazz = clazz;
    this.layoutId = layoutId;
  }

  @Override
  protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
    return items.get(position) != null && items.get(position).getClass() == clazz;
  }

  @NonNull
  @Override
  protected ViewHolder onCreateViewHolder(ViewGroup parent) {
    View itemView = layoutInflaterManager.inflate(layoutId, parent, false);
    RadarViewHolder<T> viewHolderDelegate = getViewHolderDelegate();
    BaseViewHolder<T> viewHolder = new BaseViewHolder<>(itemView, viewHolderDelegate);
    return viewHolder;
  }

  protected abstract RadarViewHolder<T> getViewHolderDelegate();

  @SuppressWarnings("unchecked")
  @Override
  protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position,
      @NonNull ViewHolder holder, @NonNull List<Object> payloads) {
    ((BaseViewHolder) holder).render(position, items.get(position));
  }
}
