/**
 * Copyright (C) 2015 ogaclejapan
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ogaclejapan.smarttablayout;

import android.view.View;
import android.view.ViewGroup;

import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;

final class Utils {

  static int getMeasuredWidth(View v) {
    return (v == null) ? 0 : v.getMeasuredWidth();
  }

  static int getWidth(View v) {
    return (v == null) ? 0 : v.getWidth();
  }

  static int getWidthWithMargin(View v) {
    return getWidth(v) + getMarginHorizontally(v);
  }

  static int getStart(View v) {
    return getStart(v, false);
  }

  static int getStart(View v, boolean withoutPadding) {
    if (v == null) {
      return 0;
    }
    if (isLayoutRtl(v)) {
      return (withoutPadding) ? v.getRight() - getPaddingStart(v) : v.getRight();
    } else {
      return (withoutPadding) ? v.getLeft() + getPaddingStart(v) : v.getLeft();
    }
  }

  static int getEnd(View v) {
    return getEnd(v, false);
  }

  static int getEnd(View v, boolean withoutPadding) {
    if (v == null) {
      return 0;
    }
    if (isLayoutRtl(v)) {
      return (withoutPadding) ? v.getLeft() + getPaddingEnd(v) : v.getLeft();
    } else {
      return (withoutPadding) ? v.getRight() - getPaddingEnd(v) : v.getRight();
    }
  }

  static int getPaddingStart(View v) {
    if (v == null) {
      return 0;
    }
    return ViewCompat.getPaddingStart(v);
  }

  static int getPaddingEnd(View v) {
    if (v == null) {
      return 0;
    }
    return ViewCompat.getPaddingEnd(v);
  }

  static int getPaddingHorizontally(View v) {
    if (v == null) {
      return 0;
    }
    return v.getPaddingLeft() + v.getPaddingRight();
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

  static boolean isLayoutRtl(View v) {
    return ViewCompat.getLayoutDirection(v) == ViewCompat.LAYOUT_DIRECTION_RTL;
  }

  private Utils() { }

}
