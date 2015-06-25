package com.ogaclejapan.smarttablayout;

import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;

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

  private Utils() { }

}
