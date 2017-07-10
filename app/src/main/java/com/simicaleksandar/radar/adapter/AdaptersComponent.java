package com.simicaleksandar.radar.adapter;

import com.simicaleksandar.radar.R;
import com.simicaleksandar.radar.adapter.viewholders.DogViewHolder;
import com.simicaleksandar.radar.adapter.viewholders.GeckoViewHolder;

import radar.AdapterFactory;
import radar.RadarAdapter;
import radar.RecyclerViewAdapter;
import radar.ViewHolder;

@AdapterFactory
public interface AdaptersComponent {

    @RecyclerViewAdapter(viewHolders = {DogViewHolder.class, GeckoViewHolder.class})
    RadarAdapter a();
}
