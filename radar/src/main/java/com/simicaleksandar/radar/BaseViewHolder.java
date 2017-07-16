package com.simicaleksandar.radar;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import radar.DisplayableItem;
import radar.RadarViewHolder;

public class BaseViewHolder<T extends DisplayableItem> extends RecyclerView.ViewHolder {

    private RadarViewHolder<T> viewHolderDelegate;

    public BaseViewHolder(View itemView, RadarViewHolder<T> viewHolderDelegate) {
        super(itemView);
        if (viewHolderDelegate == null) {
            throw new IllegalArgumentException("View holder delegate cant be null");
        }

        this.viewHolderDelegate = viewHolderDelegate;
        this.viewHolderDelegate.onCreateViewHolder(itemView);
    }

    public final void render(int position, T item) {
        viewHolderDelegate.onBindViewHolder(itemView, position, item);
    }
}
