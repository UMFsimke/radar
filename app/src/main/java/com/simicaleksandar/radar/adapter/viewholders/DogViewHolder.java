package com.simicaleksandar.radar.adapter.viewholders;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.simicaleksandar.radar.R;
import com.simicaleksandar.radar.datamodel.DogDataModel;

import radar.RadarViewHolder;
import radar.ViewHolder;

@ViewHolder(layoutId = R.layout.dog_desc)
public class DogViewHolder implements RadarViewHolder<DogDataModel>{

    private TextView dogDescription;

    @Override
    public void onCreateViewHolder(View itemView) {
        dogDescription = (TextView) itemView.findViewById(R.id.dog_desc);
    }

    @Override
    public void onBindViewHolder(@NonNull View itemView, int position, @Nullable DogDataModel item) {
        dogDescription.setText(item.getColorfulDogRace());
    }
}
