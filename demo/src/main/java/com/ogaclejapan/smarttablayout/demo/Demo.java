package com.ogaclejapan.smarttablayout.demo;

import android.content.Context;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

public enum Demo {

    BASIC(R.string.demo_title_basic, R.layout.demo_basic),

    SMART_INDICATOR(R.string.demo_title_smart_indicator, R.layout.demo_smart_indicator),

    DISTRIBUTE_EVENLY(R.string.demo_title_distribute_evenly, R.layout.demo_distribute_evenly) {
        @Override
        public int[] tabs() {
            return tab3();
        }
    },

    ALWAYS_IN_CENTER(R.string.demo_title_always_in_center, R.layout.demo_always_in_center),

    CUSTOM_TAB(R.string.demo_title_custom_tab_text, R.layout.demo_custom_tab_text),

    CUSTOM_TAB_COLORS(R.string.demo_title_custom_tab_colors, R.layout.demo_custom_tab_colors),

    INDICATOR_TRICK(R.string.demo_title_indicator_trick, R.layout.demo_indicator_trick);

    public final int titleResId;
    public final int layoutResId;

    Demo(int titleResId, int layoutResId) {
        this.titleResId = titleResId;
        this.layoutResId = layoutResId;
    }

    public void startActivity(Context context) {
        DemoActivity.startActivity(context, this);
    }

    public void setup(final SmartTabLayout layout) {
        //Do nothing.
    }

    public int[] tabs() {
        return tab10();
    }

    public static int[] tab10() {
        return new int[]{
                R.string.demo_tab_1,
                R.string.demo_tab_2,
                R.string.demo_tab_3,
                R.string.demo_tab_4,
                R.string.demo_tab_5,
                R.string.demo_tab_6,
                R.string.demo_tab_7,
                R.string.demo_tab_8,
                R.string.demo_tab_9,
                R.string.demo_tab_10
        };
    }

    public static int[] tab3() {
        return new int[]{
                R.string.demo_tab_8,
                R.string.demo_tab_9,
                R.string.demo_tab_10
        };
    }

}
