package radar;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

public interface RadarViewHolder<T extends DisplayableItem> {
  void onCreateViewHolder(View itemView);
  void onBindViewHolder(@NonNull View itemView, int position, @Nullable T item);
}
