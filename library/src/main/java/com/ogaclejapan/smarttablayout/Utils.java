/**
 * Copyright (C) 2015 ogaclejapan
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ogaclejapan.smarttablayout;

import android.content.Context;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

final class Utils {

  static int getMeasuredWidth(View v) {
    return (v == null) ? 0 : v.getMeasuredWidth();
  }

  static int getWidth(View v) {
    return (v == null) ? 0 : v.getWidth();
  }

  static int getRight(View v) {
    return getRight(v, false);
  }

  static int getRight(View v, boolean withoutPadding) {
    if (v == null) {
      return 0;
    }
    return (withoutPadding) ? v.getRight() - ViewCompat.getPaddingEnd(v) : v.getRight();
  }

  static int getLeft(View v) {
    return getLeft(v, false);
  }

  static int getLeft(View v, boolean withoutPadding) {
    if (v == null) {
      return 0;
    }
    return (withoutPadding) ? v.getLeft() + ViewCompat.getPaddingStart(v) : v.getLeft();
  }

  static int getMarginStart(View v) {
    if (v == null) {
      return 0;
    }
    ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
    return MarginLayoutParamsCompat.getMarginStart(lp);
  }

  static int getMarginEnd(View v) {
    if (v == null) {
      return 0;
    }
    ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
    return MarginLayoutParamsCompat.getMarginEnd(lp);
  }

  static int getMarginHorizontally(View v) {
    if (v == null) {
      return 0;
    }
    ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
    return MarginLayoutParamsCompat.getMarginStart(lp) + MarginLayoutParamsCompat.getMarginEnd(lp);
  }

  static DisplayMetrics getDisplayMetrics(final Context context) {
    final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    final DisplayMetrics metrics = new DisplayMetrics();
    wm.getDefaultDisplay().getMetrics(metrics);
    return metrics;
  }

  private Utils() { }

}
