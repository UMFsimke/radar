package com.simicaleksandar.radar.inner;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

final class LayoutInflaterManager {

  private static transient LayoutInflaterManager instance;
  private final transient LayoutInflater inflater;

  public static LayoutInflaterManager getInstance(Activity activity) {
    if (instance == null) {
      instance = new LayoutInflaterManager(activity);
    }

    return instance;
  }

  private LayoutInflaterManager(Activity activity) {
    inflater = activity.getLayoutInflater();
  }

  public View inflate(@LayoutRes int layoutId, @Nullable ViewGroup root, boolean attachToRoot) {
    assert inflater != null;

    return inflater.inflate(layoutId, root, attachToRoot);
  }
}
