package com.simicaleksandar.radar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.simicaleksandar.radar.adapter.DogGeckoAdapterRadar;
import com.simicaleksandar.radar.datamodel.DogDataModel;
import com.simicaleksandar.radar.datamodel.GeckoDataModel;
import com.simicaleksandar.radar.model.Dog;
import com.simicaleksandar.radar.model.Gecko;

import java.util.ArrayList;
import java.util.List;

import radar.DisplayableItem;

/**
 * Created by aleksandarsimic on 6/2/17.
 */

public class GeckosAndDogsActivity extends AppCompatActivity {

    private RecyclerView geckosAndDogsRecyclerView;
    List<DisplayableItem> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geckos_and_dogs);
        setupGeckosAndDogsRecyclerView();
        generateGeckosAndDogs();
        renderGeckosAndDogs();
    }

    private void setupGeckosAndDogsRecyclerView() {
        geckosAndDogsRecyclerView = (RecyclerView) findViewById(R.id.geckos_and_dogs_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        geckosAndDogsRecyclerView.setLayoutManager(manager);
    }

    private void generateGeckosAndDogs() {
        generateGeckos();
        generateDogs();

    }

    private void generateGeckos() {
        List<Gecko> geckos = new ArrayList<>(3);
        geckos.add(new Gecko("Gecko1", "Blue"));
        geckos.add(new Gecko("Gecko2", "Red"));
        geckos.add(new Gecko("Gecko3", "Green"));

        items = new ArrayList<>();
        items.add(GeckoDataModel.newInstance(geckos.get(0)));
        items.add(GeckoDataModel.newInstance(geckos.get(1)));
        items.add(GeckoDataModel.newInstance(geckos.get(2)));
    }

    private void generateDogs() {
        List<Dog> dogs = new ArrayList<>(3);
        dogs.add(new Dog("Dog1", "Yellow"));
        dogs.add(new Dog("Dog2", "Brown"));
        dogs.add(new Dog("Dog3", "Black"));

        items.add(DogDataModel.newInstance(dogs.get(0)));
        items.add(DogDataModel.newInstance(dogs.get(1)));
        items.add(DogDataModel.newInstance(dogs.get(2)));
    }

    private void renderGeckosAndDogs() {
        DogGeckoAdapterRadar adapter = new DogGeckoAdapterRadar(this);
        adapter.addAll(items);
        geckosAndDogsRecyclerView.setAdapter(adapter);
    }
}
