package com.simicaleksandar.radar.adapter;

import com.simicaleksandar.radar.adapter.viewholders.DogViewHolder;
import com.simicaleksandar.radar.adapter.viewholders.GeckoViewHolder;

import radar.AdapterFactory;
import radar.RadarAdapter;
import radar.RecyclerViewAdapter;

@AdapterFactory
public interface Factory {

    @RecyclerViewAdapter(viewHolders = {DogViewHolder.class, GeckoViewHolder.class})
    RadarAdapter b();
}
