package radar;

import java.util.List;

public interface RadarAdapter {
  int getItemCount();
  void replaceWith(List<DisplayableItem> items);
  void add(DisplayableItem item);
  void add(int position, DisplayableItem item);
  void addAll(int position, List<DisplayableItem> items);
  void addAll(List<DisplayableItem> items);
  DisplayableItem remove(int position);
  boolean removeAll(List<DisplayableItem> items);
}
