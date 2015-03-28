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
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * To be used with ViewPager to provide a tab indicator component which give constant feedback as to
 * the user's scroll progress.
 * <p>
 * To use the component, simply add it to your view hierarchy. Then in your
 * {@link android.app.Activity} or {@link android.app.Fragment}, {@link android.support.v4.app.Fragment} call
 * {@link #setViewPager(android.support.v4.view.ViewPager)} providing it the ViewPager this layout is being used for.
 * <p>
 * The colors can be customized in two ways. The first and simplest is to provide an array of colors
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

    private static final boolean DEFAULT_DISTRIBUTE_EVENLY = false;

    private static final int TITLE_OFFSET_DIPS = 24;
    private static final int TAB_VIEW_PADDING_DIPS = 16;
    private static final boolean TAB_VIEW_TEXT_ALL_CAPS = true;
    private static final int TAB_VIEW_TEXT_SIZE_SP = 12;
    private static final int TAB_VIEW_TEXT_COLOR = 0xFC000000;
    private static final int TAB_VIEW_TEXT_MIN_WIDTH = 0;

    private int mTitleOffset;

    private boolean mTabViewTextAllCaps;
    private int mTabViewTextColor;
    private float mTabViewTextSize;
    private int mTabViewTextHorizontalPadding;
    private int mTabViewTextMinWidth;

    private int mTabViewLayoutId;
    private int mTabViewTextViewId;

    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;

    private boolean mDistributeEvenly;

    protected final SmartTabStrip mTabStrip;

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

        boolean textAllCaps = TAB_VIEW_TEXT_ALL_CAPS;
        int textColor = TAB_VIEW_TEXT_COLOR;
        float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP, dm);
        int textHorizontalPadding = (int) (TAB_VIEW_PADDING_DIPS * density);
        int textMinWidth = (int) (TAB_VIEW_TEXT_MIN_WIDTH * density);
        boolean distributeEvenly = DEFAULT_DISTRIBUTE_EVENLY;
        int textLayoutId = NO_ID;
        int textViewId = NO_ID;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.stl_SmartTabLayout, defStyle, 0);
        textAllCaps = a.getBoolean(R.styleable.stl_SmartTabLayout_stl_defaultTabTextAllCaps, textAllCaps);
        textColor = a.getColor(R.styleable.stl_SmartTabLayout_stl_defaultTabTextColor, textColor);
        textSize = a.getDimension(R.styleable.stl_SmartTabLayout_stl_defaultTabTextSize, textSize);
        textHorizontalPadding = a.getDimensionPixelSize(R.styleable.stl_SmartTabLayout_stl_defaultTabTextHorizontalPadding, textHorizontalPadding);
        textMinWidth = a.getDimensionPixelSize(R.styleable.stl_SmartTabLayout_stl_defaultTabTextMinWidth, textMinWidth);
        textLayoutId = a.getResourceId(R.styleable.stl_SmartTabLayout_stl_customTabTextLayoutId, textLayoutId);
        textViewId = a.getResourceId(R.styleable.stl_SmartTabLayout_stl_customTabTextViewId, textViewId);
        distributeEvenly = a.getBoolean(R.styleable.stl_SmartTabLayout_stl_distributeEvenly, distributeEvenly);
        a.recycle();

        mTitleOffset = (int) (TITLE_OFFSET_DIPS * density);
        mTabViewTextAllCaps = textAllCaps;
        mTabViewTextColor = textColor;
        mTabViewTextSize = textSize;
        mTabViewTextHorizontalPadding = textHorizontalPadding;
        mTabViewTextMinWidth = textMinWidth;
        mTabViewLayoutId = textLayoutId;
        mTabViewTextViewId = textViewId;
        mDistributeEvenly = distributeEvenly;

        mTabStrip = new SmartTabStrip(context, attrs);

        if (distributeEvenly && mTabStrip.isIndicatorAlwaysInCenter()) {
            throw new UnsupportedOperationException(
                    "'distributeEvenly' and 'indicatorAlwaysInCenter' both use does not support");
        }

        addView(mTabStrip, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mTabStrip.isIndicatorAlwaysInCenter() && getChildCount() > 0) {
            int left = (w - mTabStrip.getChildMeasuredWidthAt(0)) / 2;
            int right = (w - mTabStrip.getChildMeasuredWidthAt(getChildCount() - 1)) / 2;
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
        mTabStrip.setIndicationInterpolator(interpolator);
    }

    /**
     * Set the custom {@link TabColorizer} to be used.
     *
     * If you only require simple custmisation then you can use
     * {@link #setSelectedIndicatorColors(int...)} and {@link #setDividerColors(int...)} to achieve
     * similar effects.
     */
    public void setCustomTabColorizer(TabColorizer tabColorizer) {
        mTabStrip.setCustomTabColorizer(tabColorizer);
    }

    /**
     * Set the same weight for tab
     */
    public void setDistributeEvenly(boolean distributeEvenly) {
        mDistributeEvenly = distributeEvenly;
    }

    /**
     * Sets the colors to be used for indicating the selected tab. These colors are treated as a
     * circular array. Providing one color will mean that all tabs are indicated with the same color.
     */
    public void setSelectedIndicatorColors(int... colors) {
        mTabStrip.setSelectedIndicatorColors(colors);
    }

    /**
     * Sets the colors to be used for tab dividers. These colors are treated as a circular array.
     * Providing one color will mean that all tabs are indicated with the same color.
     */
    public void setDividerColors(int... colors) {
        mTabStrip.setDividerColors(colors);
    }

    /**
     * Set the {@link ViewPager.OnPageChangeListener}. When using {@link SmartTabLayout} you are
     * required to set any {@link ViewPager.OnPageChangeListener} through this method. This is so
     * that the layout can update it's scroll position correctly.
     *
     * @see ViewPager#setOnPageChangeListener(ViewPager.OnPageChangeListener)
     */
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mViewPagerPageChangeListener = listener;
    }

    /**
     * Set the custom layout to be inflated for the tab views.
     *
     * @param layoutResId Layout id to be inflated
     * @param textViewId id of the {@link android.widget.TextView} in the inflated view
     */
    public void setCustomTabView(int layoutResId, int textViewId) {
        mTabViewLayoutId = layoutResId;
        mTabViewTextViewId = textViewId;
    }

    /**
     * Sets the associated view pager. Note that the assumption here is that the pager content
     * (number of tabs and tab titles) does not change after this call has been made.
     */
    public void setViewPager(ViewPager viewPager) {
        mTabStrip.removeAllViews();

        mViewPager = viewPager;
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(new InternalViewPagerListener());
            populateTabStrip();
        }
    }

    /**
     * Create a default view to be used for tabs. This is called if a custom tab view is not set via
     * {@link #setCustomTabView(int, int)}.
     */
    protected TextView createDefaultTabView(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(mTabViewTextColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabViewTextSize);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // If we're running on Honeycomb or newer, then we can use the Theme's
            // selectableItemBackground to ensure that the View has a pressed state
            TypedValue outValue = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground,
                    outValue, true);
            textView.setBackgroundResource(outValue.resourceId);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            // If we're running on ICS or newer, enable all-caps to match the Action Bar tab style
            textView.setAllCaps(mTabViewTextAllCaps);
        }

        textView.setPadding(
                mTabViewTextHorizontalPadding, 0,
                mTabViewTextHorizontalPadding, 0);


        if (mTabViewTextMinWidth > 0) {
            textView.setMinWidth(mTabViewTextMinWidth);
        }

        return textView;
    }

    private void populateTabStrip() {
        final PagerAdapter adapter = mViewPager.getAdapter();
        final OnClickListener tabClickListener = new TabClickListener();

        final LayoutInflater inflater = LayoutInflater.from(getContext());
        for (int i = 0; i < adapter.getCount(); i++) {
            View tabView = null;
            TextView tabTitleView = null;

            if (mTabViewLayoutId != NO_ID) {
                // If there is a custom tab view layout id set, try and inflate it
                tabView = inflater.inflate(mTabViewLayoutId, mTabStrip, false);
            }

            if (mTabViewTextViewId != NO_ID && tabView != null) {
                tabTitleView = (TextView) tabView.findViewById(mTabViewTextViewId);
            }

            if (tabView == null) {
                tabView = createDefaultTabView(getContext());
            }

            if (tabTitleView == null && TextView.class.isInstance(tabView)) {
                tabTitleView = (TextView) tabView;
            }

            if (tabTitleView == null) {
                throw new IllegalStateException("tabTitleView not found.");
            }

            if (mDistributeEvenly) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                lp.width = 0;
                lp.weight = 1;
            }

            tabTitleView.setText(adapter.getPageTitle(i));
            tabView.setOnClickListener(tabClickListener);

            mTabStrip.addView(tabView);

            if (i == mViewPager.getCurrentItem()) {
                tabView.setSelected(true);
            }

        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (mViewPager != null) {
            scrollToTab(mViewPager.getCurrentItem(), 0);
        }
    }

    private void scrollToTab(int tabIndex, int positionOffset) {
        final int tabStripChildCount = mTabStrip.getChildCount();
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return;
        }

        View selectedChild = mTabStrip.getChildAt(tabIndex);
        if (selectedChild != null) {
            int targetScrollX = selectedChild.getLeft() + positionOffset;
            if (mTabStrip.isIndicatorAlwaysInCenter()) {
                targetScrollX -= (mTabStrip.getChildWidthAt(0) - selectedChild.getWidth()) / 2;
            } else if (tabIndex > 0 || positionOffset > 0) {
                // If we're not at the first child and are mid-scroll, make sure we obey the offset
                targetScrollX -= mTitleOffset;
            }

            scrollTo(targetScrollX, 0);
        }
    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {
        private int mScrollState;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int tabStripChildCount = mTabStrip.getChildCount();
            if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
                return;
            }

            mTabStrip.onViewPagerPageChanged(position, positionOffset);

            View selectedTitle = mTabStrip.getChildAt(position);
            int extraOffset = (selectedTitle != null)
                    ? (int) (positionOffset * selectedTitle.getWidth())
                    : 0;

            if (0f < positionOffset && positionOffset < 1f
                    && mTabStrip.isIndicatorAlwaysInCenter()) {
                int current = mTabStrip.getChildWidthAt(position) / 2;
                int next = mTabStrip.getChildWidthAt(position + 1) / 2;
                extraOffset = Math.round(positionOffset * (current + next));
            }

            scrollToTab(position, extraOffset);

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrolled(position, positionOffset,
                        positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mScrollState = state;

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                mTabStrip.onViewPagerPageChanged(position, 0f);
                scrollToTab(position, 0);
            }

            for (int i = 0, size = mTabStrip.getChildCount(); i < size; i++) {
                mTabStrip.getChildAt(i).setSelected(position == i);
            }

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageSelected(position);
            }
        }

    }

    private class TabClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                if (v == mTabStrip.getChildAt(i)) {
                    mViewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    }

}
