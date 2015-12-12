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

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

/**
 * <p>
 * Forked from Google Samples &gt; SlidingTabsBasic &gt;
 * <a href="https://developer.android.com/samples/SlidingTabsBasic/src/com.example.android.common/view/SlidingTabLayout.html">SlidingTabStrip</a>
 */
class SmartTabStrip extends LinearLayout {

  private static final int GRAVITY_BOTTOM = 0;
  private static final int GRAVITY_TOP = 1;
  private static final int GRAVITY_CENTER = 2;

  private static final int AUTO_WIDTH = -1;

  private static final int DEFAULT_TOP_BORDER_THICKNESS_DIPS = 0;
  private static final byte DEFAULT_TOP_BORDER_COLOR_ALPHA = 0x26;
  private static final int DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 2;
  private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 0x26;
  private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 8;
  private static final int DEFAULT_SELECTED_INDICATOR_COLOR = 0xFF33B5E5;
  private static final float DEFAULT_INDICATOR_CORNER_RADIUS = 0f;
  private static final int DEFAULT_DIVIDER_THICKNESS_DIPS = 1;
  private static final byte DEFAULT_DIVIDER_COLOR_ALPHA = 0x20;
  private static final float DEFAULT_DIVIDER_HEIGHT = 0.5f;
  private static final boolean DEFAULT_INDICATOR_IN_CENTER = false;
  private static final boolean DEFAULT_INDICATOR_IN_FRONT = false;
  private static final boolean DEFAULT_INDICATOR_WITHOUT_PADDING = false;
  private static final int DEFAULT_INDICATOR_GRAVITY = GRAVITY_BOTTOM;
  private static final boolean DEFAULT_DRAW_DECORATION_AFTER_TAB = false;

  private final int topBorderThickness;
  private final int topBorderColor;
  private final int bottomBorderThickness;
  private final int bottomBorderColor;
  private final Paint borderPaint;
  private final RectF indicatorRectF = new RectF();
  private final boolean indicatorWithoutPadding;
  private final boolean indicatorAlwaysInCenter;
  private final boolean indicatorInFront;
  private final int indicatorThickness;
  private final int indicatorWidth;
  private final int indicatorGravity;
  private final float indicatorCornerRadius;
  private final Paint indicatorPaint;
  private final int dividerThickness;
  private final Paint dividerPaint;
  private final float dividerHeight;
  private final SimpleTabColorizer defaultTabColorizer;
  private final boolean drawDecorationAfterTab;

  private int lastPosition;
  private int selectedPosition;
  private float selectionOffset;
  private SmartTabIndicationInterpolator indicationInterpolator;
  private SmartTabLayout.TabColorizer customTabColorizer;

  SmartTabStrip(Context context, AttributeSet attrs) {
    super(context);
    setWillNotDraw(false);

    final float density = getResources().getDisplayMetrics().density;

    TypedValue outValue = new TypedValue();
    context.getTheme().resolveAttribute(android.R.attr.colorForeground, outValue, true);
    final int themeForegroundColor = outValue.data;

    boolean indicatorWithoutPadding = DEFAULT_INDICATOR_WITHOUT_PADDING;
    boolean indicatorInFront = DEFAULT_INDICATOR_IN_FRONT;
    boolean indicatorAlwaysInCenter = DEFAULT_INDICATOR_IN_CENTER;
    int indicationInterpolatorId = SmartTabIndicationInterpolator.ID_SMART;
    int indicatorGravity = DEFAULT_INDICATOR_GRAVITY;
    int indicatorColor = DEFAULT_SELECTED_INDICATOR_COLOR;
    int indicatorColorsId = NO_ID;
    int indicatorThickness = (int) (SELECTED_INDICATOR_THICKNESS_DIPS * density);
    int indicatorWidth = AUTO_WIDTH;
    float indicatorCornerRadius = DEFAULT_INDICATOR_CORNER_RADIUS * density;
    int overlineColor = setColorAlpha(themeForegroundColor, DEFAULT_TOP_BORDER_COLOR_ALPHA);
    int overlineThickness = (int) (DEFAULT_TOP_BORDER_THICKNESS_DIPS * density);
    int underlineColor = setColorAlpha(themeForegroundColor, DEFAULT_BOTTOM_BORDER_COLOR_ALPHA);
    int underlineThickness = (int) (DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS * density);
    int dividerColor = setColorAlpha(themeForegroundColor, DEFAULT_DIVIDER_COLOR_ALPHA);
    int dividerColorsId = NO_ID;
    int dividerThickness = (int) (DEFAULT_DIVIDER_THICKNESS_DIPS * density);
    boolean drawDecorationAfterTab = DEFAULT_DRAW_DECORATION_AFTER_TAB;

    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.stl_SmartTabLayout);
    indicatorAlwaysInCenter = a.getBoolean(
        R.styleable.stl_SmartTabLayout_stl_indicatorAlwaysInCenter, indicatorAlwaysInCenter);
    indicatorWithoutPadding = a.getBoolean(
        R.styleable.stl_SmartTabLayout_stl_indicatorWithoutPadding, indicatorWithoutPadding);
    indicatorInFront = a.getBoolean(
        R.styleable.stl_SmartTabLayout_stl_indicatorInFront, indicatorInFront);
    indicationInterpolatorId = a.getInt(
        R.styleable.stl_SmartTabLayout_stl_indicatorInterpolation, indicationInterpolatorId);
    indicatorGravity = a.getInt(
        R.styleable.stl_SmartTabLayout_stl_indicatorGravity, indicatorGravity);
    indicatorColor = a.getColor(
        R.styleable.stl_SmartTabLayout_stl_indicatorColor, indicatorColor);
    indicatorColorsId = a.getResourceId(
        R.styleable.stl_SmartTabLayout_stl_indicatorColors, indicatorColorsId);
    indicatorThickness = a.getDimensionPixelSize(
        R.styleable.stl_SmartTabLayout_stl_indicatorThickness, indicatorThickness);
    indicatorWidth = a.getLayoutDimension(
        R.styleable.stl_SmartTabLayout_stl_indicatorWidth, indicatorWidth);
    indicatorCornerRadius = a.getDimension(
        R.styleable.stl_SmartTabLayout_stl_indicatorCornerRadius, indicatorCornerRadius);
    overlineColor = a.getColor(
        R.styleable.stl_SmartTabLayout_stl_overlineColor, overlineColor);
    overlineThickness = a.getDimensionPixelSize(
        R.styleable.stl_SmartTabLayout_stl_overlineThickness, overlineThickness);
    underlineColor = a.getColor(
        R.styleable.stl_SmartTabLayout_stl_underlineColor, underlineColor);
    underlineThickness = a.getDimensionPixelSize(
        R.styleable.stl_SmartTabLayout_stl_underlineThickness, underlineThickness);
    dividerColor = a.getColor(
        R.styleable.stl_SmartTabLayout_stl_dividerColor, dividerColor);
    dividerColorsId = a.getResourceId(
        R.styleable.stl_SmartTabLayout_stl_dividerColors, dividerColorsId);
    dividerThickness = a.getDimensionPixelSize(
        R.styleable.stl_SmartTabLayout_stl_dividerThickness, dividerThickness);
    drawDecorationAfterTab = a.getBoolean(
        R.styleable.stl_SmartTabLayout_stl_drawDecorationAfterTab, drawDecorationAfterTab);
    a.recycle();

    final int[] indicatorColors = (indicatorColorsId == NO_ID)
        ? new int[] { indicatorColor }
        : getResources().getIntArray(indicatorColorsId);

    final int[] dividerColors = (dividerColorsId == NO_ID)
        ? new int[] { dividerColor }
        : getResources().getIntArray(dividerColorsId);

    this.defaultTabColorizer = new SimpleTabColorizer();
    this.defaultTabColorizer.setIndicatorColors(indicatorColors);
    this.defaultTabColorizer.setDividerColors(dividerColors);

    this.topBorderThickness = overlineThickness;
    this.topBorderColor = overlineColor;
    this.bottomBorderThickness = underlineThickness;
    this.bottomBorderColor = underlineColor;
    this.borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    this.indicatorAlwaysInCenter = indicatorAlwaysInCenter;
    this.indicatorWithoutPadding = indicatorWithoutPadding;
    this.indicatorInFront = indicatorInFront;
    this.indicatorThickness = indicatorThickness;
    this.indicatorWidth = indicatorWidth;
    this.indicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    this.indicatorCornerRadius = indicatorCornerRadius;
    this.indicatorGravity = indicatorGravity;

    this.dividerHeight = DEFAULT_DIVIDER_HEIGHT;
    this.dividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    this.dividerPaint.setStrokeWidth(dividerThickness);
    this.dividerThickness = dividerThickness;

    this.drawDecorationAfterTab = drawDecorationAfterTab;

    this.indicationInterpolator = SmartTabIndicationInterpolator.of(indicationInterpolatorId);
  }

  /**
   * Set the alpha value of the {@code color} to be the given {@code alpha} value.
   */
  private static int setColorAlpha(int color, byte alpha) {
    return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
  }

  /**
   * Blend {@code color1} and {@code color2} using the given ratio.
   *
   * @param ratio of which to blend. 1.0 will return {@code color1}, 0.5 will give an even blend,
   * 0.0 will return {@code color2}.
   */
  private static int blendColors(int color1, int color2, float ratio) {
    final float inverseRation = 1f - ratio;
    float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
    float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
    float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
    return Color.rgb((int) r, (int) g, (int) b);
  }

  void setIndicationInterpolator(SmartTabIndicationInterpolator interpolator) {
    indicationInterpolator = interpolator;
    invalidate();
  }

  void setCustomTabColorizer(SmartTabLayout.TabColorizer customTabColorizer) {
    this.customTabColorizer = customTabColorizer;
    invalidate();
  }

  void setSelectedIndicatorColors(int... colors) {
    // Make sure that the custom colorizer is removed
    customTabColorizer = null;
    defaultTabColorizer.setIndicatorColors(colors);
    invalidate();
  }

  void setDividerColors(int... colors) {
    // Make sure that the custom colorizer is removed
    customTabColorizer = null;
    defaultTabColorizer.setDividerColors(colors);
    invalidate();
  }

  void onViewPagerPageChanged(int position, float positionOffset) {
    selectedPosition = position;
    selectionOffset = positionOffset;
    if (positionOffset == 0f && lastPosition != selectedPosition) {
      lastPosition = selectedPosition;
    }
    invalidate();
  }

  boolean isIndicatorAlwaysInCenter() {
    return indicatorAlwaysInCenter;
  }

  SmartTabLayout.TabColorizer getTabColorizer() {
    return (customTabColorizer != null) ? customTabColorizer : defaultTabColorizer;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if (!drawDecorationAfterTab) {
      drawDecoration(canvas);
    }
  }

  @Override
  protected void dispatchDraw(Canvas canvas) {
    super.dispatchDraw(canvas);
    if (drawDecorationAfterTab) {
      drawDecoration(canvas);
    }
  }

  private void drawDecoration(Canvas canvas) {
    final int height = getHeight();
    final int width = getWidth();
    final int tabCount = getChildCount();
    final SmartTabLayout.TabColorizer tabColorizer = getTabColorizer();
    final boolean isLayoutRtl = Utils.isLayoutRtl(this);

    if (indicatorInFront) {
      drawOverline(canvas, 0, width);
      drawUnderline(canvas, 0, width, height);
    }

    // Thick colored underline below the current selection
    if (tabCount > 0) {
      View selectedTab = getChildAt(selectedPosition);
      int selectedStart = Utils.getStart(selectedTab, indicatorWithoutPadding);
      int selectedEnd = Utils.getEnd(selectedTab, indicatorWithoutPadding);
      int left;
      int right;
      if (isLayoutRtl) {
        left = selectedEnd;
        right = selectedStart;
      } else {
        left = selectedStart;
        right = selectedEnd;
      }

      int color = tabColorizer.getIndicatorColor(selectedPosition);
      float thickness = indicatorThickness;

      if (selectionOffset > 0f && selectedPosition < (getChildCount() - 1)) {
        int nextColor = tabColorizer.getIndicatorColor(selectedPosition + 1);
        if (color != nextColor) {
          color = blendColors(nextColor, color, selectionOffset);
        }

        // Draw the selection partway between the tabs
        float startOffset = indicationInterpolator.getLeftEdge(selectionOffset);
        float endOffset = indicationInterpolator.getRightEdge(selectionOffset);
        float thicknessOffset = indicationInterpolator.getThickness(selectionOffset);

        View nextTab = getChildAt(selectedPosition + 1);
        int nextStart = Utils.getStart(nextTab, indicatorWithoutPadding);
        int nextEnd = Utils.getEnd(nextTab, indicatorWithoutPadding);
        if (isLayoutRtl) {
          left = (int) (endOffset * nextEnd + (1.0f - endOffset) * left);
          right = (int) (startOffset * nextStart + (1.0f - startOffset) * right);
        } else {
          left = (int) (startOffset * nextStart + (1.0f - startOffset) * left);
          right = (int) (endOffset * nextEnd + (1.0f - endOffset) * right);
        }
        thickness = thickness * thicknessOffset;
      }

      drawIndicator(canvas, left, right, height, thickness, color);

    }

    if (!indicatorInFront) {
      drawOverline(canvas, 0, width);
      drawUnderline(canvas, 0, getWidth(), height);
    }

    // Vertical separators between the titles
    drawSeparator(canvas, height, tabCount);

  }

  private void drawSeparator(Canvas canvas, int height, int tabCount) {
    if (dividerThickness <= 0) {
      return;
    }

    final int dividerHeightPx = (int) (Math.min(Math.max(0f, dividerHeight), 1f) * height);
    final SmartTabLayout.TabColorizer tabColorizer = getTabColorizer();

    // Vertical separators between the titles
    final int separatorTop = (height - dividerHeightPx) / 2;
    final int separatorBottom = separatorTop + dividerHeightPx;

    final boolean isLayoutRtl = Utils.isLayoutRtl(this);
    for (int i = 0; i < tabCount - 1; i++) {
      View child = getChildAt(i);
      int end = Utils.getEnd(child);
      int endMargin = Utils.getMarginEnd(child);
      int separatorX = isLayoutRtl ? end - endMargin : end + endMargin;
      dividerPaint.setColor(tabColorizer.getDividerColor(i));
      canvas.drawLine(separatorX, separatorTop, separatorX, separatorBottom, dividerPaint);
    }
  }

  private void drawIndicator(Canvas canvas, int left, int right, int height, float thickness,
      int color) {
    if (indicatorThickness <= 0 || indicatorWidth == 0) {
      return;
    }

    float center;
    float top;
    float bottom;

    switch (indicatorGravity) {
      case GRAVITY_TOP:
        center = indicatorThickness / 2f;
        top = center - (thickness / 2f);
        bottom = center + (thickness / 2f);
        break;
      case GRAVITY_CENTER:
        center = height / 2f;
        top = center - (thickness / 2f);
        bottom = center + (thickness / 2f);
        break;
      case GRAVITY_BOTTOM:
      default:
        center = height - (indicatorThickness / 2f);
        top = center - (thickness / 2f);
        bottom = center + (thickness / 2f);
    }

    indicatorPaint.setColor(color);
    if (indicatorWidth == AUTO_WIDTH) {
      indicatorRectF.set(left, top, right, bottom);
    } else {
      float padding = (Math.abs(left - right) - indicatorWidth) / 2f;
      indicatorRectF.set(left + padding, top, right - padding, bottom);
    }

    if (indicatorCornerRadius > 0f) {
      canvas.drawRoundRect(
          indicatorRectF, indicatorCornerRadius,
          indicatorCornerRadius, indicatorPaint);
    } else {
      canvas.drawRect(indicatorRectF, indicatorPaint);
    }
  }

  private void drawOverline(Canvas canvas, int left, int right) {
    if (topBorderThickness <= 0) {
      return;
    }
    // Thin overline along the entire top edge
    borderPaint.setColor(topBorderColor);
    canvas.drawRect(left, 0, right, topBorderThickness, borderPaint);
  }

  private void drawUnderline(Canvas canvas, int left, int right, int height) {
    if (bottomBorderThickness <= 0) {
      return;
    }
    // Thin underline along the entire bottom edge
    borderPaint.setColor(bottomBorderColor);
    canvas.drawRect(left, height - bottomBorderThickness, right, height, borderPaint);
  }

  private static class SimpleTabColorizer implements SmartTabLayout.TabColorizer {

    private int[] indicatorColors;
    private int[] dividerColors;

    @Override
    public final int getIndicatorColor(int position) {
      return indicatorColors[position % indicatorColors.length];
    }

    @Override
    public final int getDividerColor(int position) {
      return dividerColors[position % dividerColors.length];
    }

    void setIndicatorColors(int... colors) {
      indicatorColors = colors;
    }

    void setDividerColors(int... colors) {
      dividerColors = colors;
    }
  }
}
