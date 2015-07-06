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
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * To be used with ViewPager to provide a tab indicator component which give constant feedback as
 * to
 * the user's scroll progress.
 * <p>
 * To use the component, simply add it to your view hierarchy. Then in your
 * {@link android.app.Activity} or {@link android.app.Fragment}, {@link
 * android.support.v4.app.Fragment} call
 * {@link #setViewPager(android.support.v4.view.ViewPager)} providing it the ViewPager this layout
 * is being used for.
 * <p>
 * The colors can be customized in two ways. The first and simplest is to provide an array of
 * colors
 * via {@link #setSelectedIndicatorColors(int...)} and {@link #setDividerColors(int...)}. The
 * alternative is via the {@link TabColorizer} interface which provides you complete control over
 * which color is used for any individual position.
 * <p>
 * The views used as tabs can be customized by calling {@link #setCustomTabView(int, int)},
 * providing the layout ID of your custom layout.
 * <p>
 * Forked from Google Samples &gt; SlidingTabsBasic &gt;
 * <a href="https://developer.android.com/samples/SlidingTabsBasic/src/com.example.android.common/view/SlidingTabLayout.html">SlidingTabLayout</a>
 */
public class SmartTabLayout extends HorizontalScrollView {

  private static final boolean DEFAULT_DISTRIBUTE_EVENLY = false;
  private static final int TITLE_OFFSET_DIPS = 24;
  private static final int TAB_VIEW_PADDING_DIPS = 16;
  private static final boolean TAB_VIEW_TEXT_ALL_CAPS = true;
  private static final int TAB_VIEW_TEXT_SIZE_SP = 12;
  private static final int TAB_VIEW_TEXT_COLOR = 0xFC000000;
  private static final int TAB_VIEW_TEXT_MIN_WIDTH = 0;

  protected final SmartTabStrip tabStrip;
  private int titleOffset;
  private int tabViewBackgroundResId;
  private boolean tabViewTextAllCaps;
  private ColorStateList tabViewTextColors;
  private float tabViewTextSize;
  private int tabViewTextHorizontalPadding;
  private int tabViewTextMinWidth;
  private ViewPager viewPager;
  private ViewPager.OnPageChangeListener viewPagerPageChangeListener;
  private OnScrollChangeListener onScrollChangeListener;
  private TabProvider tabProvider;
  private boolean distributeEvenly;

  public SmartTabLayout(Context context) {
    this(context, null);
  }

  public SmartTabLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SmartTabLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    // Disable the Scroll Bar
    setHorizontalScrollBarEnabled(false);
    // Make sure that the Tab Strips fills this View
    setFillViewport(true);

    final DisplayMetrics dm = getResources().getDisplayMetrics();
    final float density = dm.density;

    int tabBackgroundResId = NO_ID;
    boolean textAllCaps = TAB_VIEW_TEXT_ALL_CAPS;
    ColorStateList textColors;
    float textSize = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP, dm);
    int textHorizontalPadding = (int) (TAB_VIEW_PADDING_DIPS * density);
    int textMinWidth = (int) (TAB_VIEW_TEXT_MIN_WIDTH * density);
    boolean distributeEvenly = DEFAULT_DISTRIBUTE_EVENLY;
    int customTabLayoutId = NO_ID;
    int customTabTextViewId = NO_ID;

    TypedArray a = context.obtainStyledAttributes(
        attrs, R.styleable.stl_SmartTabLayout, defStyle, 0);
    tabBackgroundResId = a.getResourceId(
        R.styleable.stl_SmartTabLayout_stl_defaultTabBackground, tabBackgroundResId);
    textAllCaps = a.getBoolean(
        R.styleable.stl_SmartTabLayout_stl_defaultTabTextAllCaps, textAllCaps);
    textColors = a.getColorStateList(
        R.styleable.stl_SmartTabLayout_stl_defaultTabTextColor);
    textSize = a.getDimension(
        R.styleable.stl_SmartTabLayout_stl_defaultTabTextSize, textSize);
    textHorizontalPadding = a.getDimensionPixelSize(
        R.styleable.stl_SmartTabLayout_stl_defaultTabTextHorizontalPadding, textHorizontalPadding);
    textMinWidth = a.getDimensionPixelSize(
        R.styleable.stl_SmartTabLayout_stl_defaultTabTextMinWidth, textMinWidth);
    customTabLayoutId = a.getResourceId(
        R.styleable.stl_SmartTabLayout_stl_customTabTextLayoutId, customTabLayoutId);
    customTabTextViewId = a.getResourceId(
        R.styleable.stl_SmartTabLayout_stl_customTabTextViewId, customTabTextViewId);
    distributeEvenly = a.getBoolean(
        R.styleable.stl_SmartTabLayout_stl_distributeEvenly, distributeEvenly);
    a.recycle();

    this.titleOffset = (int) (TITLE_OFFSET_DIPS * density);
    this.tabViewBackgroundResId = tabBackgroundResId;
    this.tabViewTextAllCaps = textAllCaps;
    this.tabViewTextColors = (textColors != null)
        ? textColors
        : ColorStateList.valueOf(TAB_VIEW_TEXT_COLOR);
    this.tabViewTextSize = textSize;
    this.tabViewTextHorizontalPadding = textHorizontalPadding;
    this.tabViewTextMinWidth = textMinWidth;
    this.distributeEvenly = distributeEvenly;

    if (customTabLayoutId != NO_ID) {
      setCustomTabView(customTabLayoutId, customTabTextViewId);
    }

    this.tabStrip = new SmartTabStrip(context, attrs);

    if (distributeEvenly && tabStrip.isIndicatorAlwaysInCenter()) {
      throw new UnsupportedOperationException(
          "'distributeEvenly' and 'indicatorAlwaysInCenter' both use does not support");
    }

    addView(tabStrip, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

  }

  @Override
  protected void onScrollChanged(final int l, final int t, final int oldl, final int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
    if (onScrollChangeListener != null) {
      onScrollChangeListener.onScrollChanged(l, t, oldl, oldt);
    }
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    if (tabStrip.isIndicatorAlwaysInCenter() && getChildCount() > 0) {
      int left = (w - tabStrip.getChildMeasuredWidthAt(0)) / 2;
      int right = (w - tabStrip.getChildMeasuredWidthAt(getChildCount() - 1)) / 2;
      setPadding(left, getPaddingTop(), right, getPaddingBottom());
      setClipToPadding(false);
    }
  }

  /**
   * Set the behavior of the Indicator scrolling feedback.
   *
   * @param interpolator {@link com.ogaclejapan.smarttablayout.SmartTabIndicationInterpolator}
   */
  public void setIndicationInterpolator(SmartTabIndicationInterpolator interpolator) {
    tabStrip.setIndicationInterpolator(interpolator);
  }

  /**
   * Set the custom {@link TabColorizer} to be used.
   *
   * If you only require simple customisation then you can use
   * {@link #setSelectedIndicatorColors(int...)} and {@link #setDividerColors(int...)} to achieve
   * similar effects.
   */
  public void setCustomTabColorizer(TabColorizer tabColorizer) {
    tabStrip.setCustomTabColorizer(tabColorizer);
  }

  /**
   * Set the color used for styling the tab text. This will need to be called prior to calling
   * {@link #setViewPager(android.support.v4.view.ViewPager)} otherwise it will not get set
   *
   * @param color to use for tab text
   */
  public void setDefaultTabTextColor(int color) {
    tabViewTextColors = ColorStateList.valueOf(color);
  }

  /**
   * Sets the colors used for styling the tab text. This will need to be called prior to calling
   * {@link #setViewPager(android.support.v4.view.ViewPager)} otherwise it will not get set
   *
   * @param colors ColorStateList to use for tab text
   */
  public void setDefaultTabTextColor(ColorStateList colors) {
    tabViewTextColors = colors;
  }

  /**
   * Set the same weight for tab
   */
  public void setDistributeEvenly(boolean distributeEvenly) {
    this.distributeEvenly = distributeEvenly;
  }

  /**
   * Sets the colors to be used for indicating the selected tab. These colors are treated as a
   * circular array. Providing one color will mean that all tabs are indicated with the same color.
   */
  public void setSelectedIndicatorColors(int... colors) {
    tabStrip.setSelectedIndicatorColors(colors);
  }

  /**
   * Sets the colors to be used for tab dividers. These colors are treated as a circular array.
   * Providing one color will mean that all tabs are indicated with the same color.
   */
  public void setDividerColors(int... colors) {
    tabStrip.setDividerColors(colors);
  }

  /**
   * Set the {@link ViewPager.OnPageChangeListener}. When using {@link SmartTabLayout} you are
   * required to set any {@link ViewPager.OnPageChangeListener} through this method. This is so
   * that the layout can update it's scroll position correctly.
   *
   * @see ViewPager#setOnPageChangeListener(ViewPager.OnPageChangeListener)
   */
  public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
    viewPagerPageChangeListener = listener;
  }

  /**
   * Set {@link OnScrollChangeListener} for obtaining values of scrolling.
   * @param listener the {@link OnScrollChangeListener} to set
   */
  public void setOnScrollChangeListener(OnScrollChangeListener listener) {
    onScrollChangeListener = listener;
  }

  /**
   * Set the custom layout to be inflated for the tab views.
   *
   * @param layoutResId Layout id to be inflated
   * @param textViewId id of the {@link android.widget.TextView} in the inflated view
   */
  public void setCustomTabView(int layoutResId, int textViewId) {
    tabProvider = new SimpleTabProvider(getContext(), layoutResId, textViewId);
  }

  /**
   * Set the custom layout to be inflated for the tab views.
   *
   * @param provider {@link TabProvider}
   */
  public void setCustomTabView(TabProvider provider) {
    tabProvider = provider;
  }

  /**
   * Sets the associated view pager. Note that the assumption here is that the pager content
   * (number of tabs and tab titles) does not change after this call has been made.
   */
  public void setViewPager(ViewPager viewPager) {
    tabStrip.removeAllViews();

    this.viewPager = viewPager;
    if (viewPager != null && viewPager.getAdapter() != null) {
      viewPager.setOnPageChangeListener(new InternalViewPagerListener());
      populateTabStrip();
    }
  }

  /**
   * Returns the view at the specified position in the tabs.
   *
   * @param position the position at which to get the view from
   * @return the view at the specified position or null if the position does not exist within the
   * tabs
   */
  public View getTabAt(int position) {
    return tabStrip.getChildAt(position);
  }

  /**
   * Create a default view to be used for tabs. This is called if a custom tab view is not set via
   * {@link #setCustomTabView(int, int)}.
   */
  protected TextView createDefaultTabView(CharSequence title) {
    TextView textView = new TextView(getContext());
    textView.setGravity(Gravity.CENTER);
    textView.setText(title);
    textView.setTextColor(tabViewTextColors);
    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabViewTextSize);
    textView.setTypeface(Typeface.DEFAULT_BOLD);
    textView.setLayoutParams(new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));

    if (tabViewBackgroundResId != NO_ID) {
      textView.setBackgroundResource(tabViewBackgroundResId);
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      // If we're running on Honeycomb or newer, then we can use the Theme's
      // selectableItemBackground to ensure that the View has a pressed state
      TypedValue outValue = new TypedValue();
      getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground,
          outValue, true);
      textView.setBackgroundResource(outValue.resourceId);
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
      // If we're running on ICS or newer, enable all-caps to match the Action Bar tab style
      textView.setAllCaps(tabViewTextAllCaps);
    }

    textView.setPadding(
        tabViewTextHorizontalPadding, 0,
        tabViewTextHorizontalPadding, 0);

    if (tabViewTextMinWidth > 0) {
      textView.setMinWidth(tabViewTextMinWidth);
    }

    return textView;
  }

  private void populateTabStrip() {
    final PagerAdapter adapter = viewPager.getAdapter();
    final OnClickListener tabClickListener = new TabClickListener();

    for (int i = 0; i < adapter.getCount(); i++) {

      final View tabView = (tabProvider == null)
          ? createDefaultTabView(adapter.getPageTitle(i))
          : tabProvider.createTabView(tabStrip, i, adapter);

      if (tabView == null) {
        throw new IllegalStateException("tabView is null.");
      }

      if (distributeEvenly) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tabView.getLayoutParams();
        lp.width = 0;
        lp.weight = 1;
      }

      tabView.setOnClickListener(tabClickListener);
      tabStrip.addView(tabView);

      if (i == viewPager.getCurrentItem()) {
        tabView.setSelected(true);
      }

    }
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();

    if (viewPager != null) {
      scrollToTab(viewPager.getCurrentItem(), 0);
    }
  }

  private void scrollToTab(int tabIndex, int positionOffset) {
    final int tabStripChildCount = tabStrip.getChildCount();
    if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
      return;
    }

    View selectedChild = tabStrip.getChildAt(tabIndex);
    if (selectedChild != null) {
      int targetScrollX = selectedChild.getLeft() + positionOffset;
      if (tabStrip.isIndicatorAlwaysInCenter()) {
        targetScrollX -= (tabStrip.getChildWidthAt(0) - selectedChild.getWidth()) / 2;
      } else if (tabIndex > 0 || positionOffset > 0) {
        // If we're not at the first child and are mid-scroll, make sure we obey the offset
        targetScrollX -= titleOffset;
      }

      scrollTo(targetScrollX, 0);
    }
  }

  /**
   * Allows complete control over the colors drawn in the tab layout. Set with
   * {@link #setCustomTabColorizer(TabColorizer)}.
   */
  public interface TabColorizer {

    /**
     * @return return the color of the indicator used when {@code position} is selected.
     */
    int getIndicatorColor(int position);

    /**
     * @return return the color of the divider drawn to the right of {@code position}.
     */
    int getDividerColor(int position);

  }

  /**
   * Create the custom tabs in the tab layout. Set with
   * {@link #setCustomTabView(com.ogaclejapan.smarttablayout.SmartTabLayout.TabProvider)}
   */
  public interface TabProvider {

    /**
     * @return Return the View of {@code position} for the Tabs
     */
    View createTabView(ViewGroup container, int position, PagerAdapter adapter);

  }

  private static class SimpleTabProvider implements TabProvider {

    private final LayoutInflater inflater;
    private final int tabViewLayoutId;
    private final int tabViewTextViewId;

    private SimpleTabProvider(Context context, int layoutResId, int textViewId) {
      inflater = LayoutInflater.from(context);
      tabViewLayoutId = layoutResId;
      tabViewTextViewId = textViewId;
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
      View tabView = null;
      TextView tabTitleView = null;

      if (tabViewLayoutId != NO_ID) {
        tabView = inflater.inflate(tabViewLayoutId, container, false);
      }

      if (tabViewTextViewId != NO_ID && tabView != null) {
        tabTitleView = (TextView) tabView.findViewById(tabViewTextViewId);
      }

      if (tabTitleView == null && TextView.class.isInstance(tabView)) {
        tabTitleView = (TextView) tabView;
      }

      if (tabTitleView != null) {
        tabTitleView.setText(adapter.getPageTitle(position));
      }

      return tabView;
    }

  }

  private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {

    private int scrollState;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      int tabStripChildCount = tabStrip.getChildCount();
      if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
        return;
      }

      tabStrip.onViewPagerPageChanged(position, positionOffset);

      View selectedTitle = tabStrip.getChildAt(position);
      int extraOffset = (selectedTitle != null)
          ? (int) (positionOffset * selectedTitle.getWidth())
          : 0;

      if (0f < positionOffset && positionOffset < 1f
          && tabStrip.isIndicatorAlwaysInCenter()) {
        int current = tabStrip.getChildWidthAt(position) / 2;
        int next = tabStrip.getChildWidthAt(position + 1) / 2;
        extraOffset = Math.round(positionOffset * (current + next));
      }

      scrollToTab(position, extraOffset);

      if (viewPagerPageChangeListener != null) {
        viewPagerPageChangeListener.onPageScrolled(position, positionOffset,
            positionOffsetPixels);
      }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
      scrollState = state;

      if (viewPagerPageChangeListener != null) {
        viewPagerPageChangeListener.onPageScrollStateChanged(state);
      }
    }

    @Override
    public void onPageSelected(int position) {
      if (scrollState == ViewPager.SCROLL_STATE_IDLE) {
        tabStrip.onViewPagerPageChanged(position, 0f);
        scrollToTab(position, 0);
      }

      for (int i = 0, size = tabStrip.getChildCount(); i < size; i++) {
        tabStrip.getChildAt(i).setSelected(position == i);
      }

      if (viewPagerPageChangeListener != null) {
        viewPagerPageChangeListener.onPageSelected(position);
      }
    }

  }

  private class TabClickListener implements OnClickListener {
    @Override
    public void onClick(View v) {
      for (int i = 0; i < tabStrip.getChildCount(); i++) {
        if (v == tabStrip.getChildAt(i)) {
          viewPager.setCurrentItem(i);
          return;
        }
      }
    }
  }

  public interface OnScrollChangeListener {
    void onScrollChanged(int l, int t, int oldl, int oldt);
  }

}
