package com.simicaleksandar.radar;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;
import java.util.ArrayList;
import java.util.List;

import radar.DisplayableItem;
import radar.RadarAdapter;

public abstract class RadarBaseAdapter extends RecyclerView.Adapter implements RadarAdapter {

  private AdapterDelegatesManager<List<DisplayableItem>> delegatesManager;
  private List<DisplayableItem> items;

  public RadarBaseAdapter(Activity activity) {
    // Delegates
    delegatesManager = new AdapterDelegatesManager<>();
    addAdapterDelegates(activity, delegatesManager);
  }

  protected abstract void addAdapterDelegates(Activity activity,
      AdapterDelegatesManager<List<DisplayableItem>> manager);

  @Override public int getItemViewType(int position) {
    return delegatesManager.getItemViewType(items, position);
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return delegatesManager.onCreateViewHolder(parent, viewType);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    delegatesManager.onBindViewHolder(items, position, holder);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
    delegatesManager.onBindViewHolder(items, position, holder, payloads);
  }

  @Override public int getItemCount() {
    return items == null ? 0 : items.size();
  }

  @Override public void replaceWith(List<DisplayableItem> items) {
    this.items = items;
    notifyDataSetChanged();
  }

  @Override public void add(DisplayableItem item) {
    add(getItemCount(), item);
  }

  @Override public void add(int position, DisplayableItem item) {
    List<DisplayableItem> items = new ArrayList<>(1);
    items.add(item);
    addAll(position, items);
  }

  @Override public void addAll(int position, List<DisplayableItem> items) {
    addAll(position, items, getItemCount());
  }

  private void addAll(int position, List<DisplayableItem> items, int count) {
    if (items == null || items.size() == 0) return;

    if (position < 0 || position > count) {
      throw new IndexOutOfBoundsException("Index: " + position +", Size: " + count);
    }

    if (this.items == null) {
      this.items = new ArrayList<>();
    }

    this.items.addAll(position, items);
    notifyItemRangeInserted(position, items.size());
  }

  @Override public void addAll(List<DisplayableItem> items) {
    addAll(getItemCount(), items);
  }

  @Override public DisplayableItem remove(int position) {
    DisplayableItem item = items.remove(position);
    notifyItemRemoved(position);
    return item;
  }

  @Override public boolean removeAll(List<DisplayableItem> items) {
    if (items == null || items.size() == 0 ||
        this.items == null || this.items.size() == 0) return true;

    List<Integer> indices = new ArrayList<>();
    int index;
    for (DisplayableItem item : items) {
      index = this.items.indexOf(item);
      if (index != -1) indices.add(index);
    }

    boolean result = this.items.removeAll(items);
    for (Integer i : indices) {
      notifyItemRemoved(i);
    }

    return result;
  }

}
