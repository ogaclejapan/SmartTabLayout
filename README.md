# SmartTabLayout
[![Maven Central][maven_central_badge_svg]][maven_central_badge_app] [![Android Arsenal][android_arsenal_badge_svg]][android_arsenal_badge_link] [![Android Weekly][android_weekly_badge_svg]][android_weekly_badge_link]

![icon][demo_icon]

A custom ViewPager title strip which gives continuous feedback to the user when scrolling.

This library has been added some features and utilities based on [android-SlidingTabBasic][google_slidingtabbasic] project of Google Samples.


![SmartTabLayout Demo1][demo1_gif] ![SmartTabLayout Demo2][demo2_gif]
![SmartTabLayout Demo3][demo3_gif] ![SmartTabLayout Demo4][demo4_gif]
![SmartTabLayout Demo5][demo5_gif] ![SmartTabLayout Demo6][demo6_gif]
![SmartTabLayout Demo7][demo7_gif]


Try out the sample application on the Play Store.

[![Get it on Google Play][googleplay_store_badge]][demo_app]


# Usage

_(For a working implementation of this project see the demo/ folder.)_

Add the dependency to your build.gradle.

```
dependencies {
    compile 'com.ogaclejapan.smarttablayout:library:1.6.1@aar'

    //Optional: see how to use the utility.
    compile 'com.ogaclejapan.smarttablayout:utils-v4:1.6.1@aar'

    //Optional: see how to use the utility.
    compile 'com.ogaclejapan.smarttablayout:utils-v13:1.6.1@aar'
}
```

Include the SmartTabLayout widget in your layout.
This should usually be placed above the ViewPager it represents.

```xml

<com.ogaclejapan.smarttablayout.SmartTabLayout
    android:id="@+id/viewpagertab"
    android:layout_width="match_parent"
    android:layout_height="48dp"
    app:stl_indicatorAlwaysInCenter="false"
    app:stl_indicatorWithoutPadding="false"
    app:stl_indicatorInFront="false"
    app:stl_indicatorInterpolation="smart"
    app:stl_indicatorGravity="bottom"
    app:stl_indicatorColor="#40C4FF"
    app:stl_indicatorThickness="4dp"
    app:stl_indicatorWidth="auto"
    app:stl_indicatorCornerRadius="2dp"
    app:stl_overlineColor="#4D000000"
    app:stl_overlineThickness="0dp"
    app:stl_underlineColor="#4D000000"
    app:stl_underlineThickness="1dp"
    app:stl_dividerColor="#4D000000"
    app:stl_dividerThickness="1dp"
    app:stl_defaultTabBackground="?attr/selectableItemBackground"
    app:stl_defaultTabTextAllCaps="true"
    app:stl_defaultTabTextColor="#FC000000"
    app:stl_defaultTabTextSize="12sp"
    app:stl_defaultTabTextHorizontalPadding="16dp"
    app:stl_defaultTabTextMinWidth="0dp"
    app:stl_distributeEvenly="false"
    app:stl_clickable="true"
    app:stl_titleOffset="24dp"
    app:stl_drawDecorationAfterTab="false"
    />

<android.support.v4.view.ViewPager
    android:id="@+id/viewpager"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_below="@id/viewpagertab"
    />

```

In your onCreate method (or onCreateView for a fragment), bind the widget to the ViewPager.
(If you use a utility together, you can easily add items to PagerAdapter)

e.g. ViewPager of v4.Fragment

```java

FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
        getSupportFragmentManager(), FragmentPagerItems.with(this)
        .add(R.string.titleA, PageFragment.class)
        .add(R.string.titleB, PageFragment.class)
        .create());

ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
viewPager.setAdapter(adapter);

SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
viewPagerTab.setViewPager(viewPager);

```

(Optional) If you use an OnPageChangeListener with your view pager you should set it in the widget rather than on the pager directly.


```java

viewPagerTab.setOnPageChangeListener(mPageChangeListener);

```

(Optional) Using the FragmentPagerItemAdapter of utility, you will be able to get a position in the Fragment side.

```java

int position = FragmentPagerItem.getPosition(getArguments());

```

This position will help to implement the parallax scrolling header that contains the ViewPager :P

# Attributes

There are several attributes you can set:

| attr | description |
|:---|:---|
| stl_indicatorAlwaysInCenter | If set to true, active tab is always displayed in center (Like Newsstand google app), default false |
| stl_indicatorWithoutPadding | If set to true, draw the indicator without padding of tab, default false |
| stl_indicatorInFront | Draw the indicator in front of the underline, default false |
| stl_indicatorInterpolation | Behavior of the indicator: 'linear' or 'smart' |
| stl_indicatorGravity | Drawing position of the indicator: 'bottom' or 'top' or 'center', default 'bottom' |
| stl_indicatorColor | Color of the indicator |
| stl_indicatorColors | Multiple colors of the indicator, can set the color for each tab |
| stl_indicatorThickness | Thickness of the indicator |
| stl_indicatorWidth | Width of the indicator, default 'auto' |
| stl_indicatorCornerRadius | Radius of rounded corner the indicator |
| stl_overlineColor | Color of the top line |
| stl_overlineThickness | Thickness of the top line |
| stl_underlineColor | Color of the bottom line |
| stl_underlineThickness | Thickness of the bottom line |
| stl_dividerColor | Color of the dividers between tabs |
| stl_dividerColors | Multiple colors of the dividers between tabs, can set the color for each tab |
| stl_dividerThickness | Thickness of the divider |
| stl_defaultTabBackground | Background drawable of each tab. In general it set the StateListDrawable |
| stl_defaultTabTextAllCaps | If set to true, all tab titles will be upper case, default true |
| stl_defaultTabTextColor | Text color of the tab that was included by default |
| stl_defaultTabTextSize | Text size of the tab that was included by default |
| stl_defaultTabTextHorizontalPadding | Text layout padding of the tab that was included by default |
| stl_defaultTabTextMinWidth | Minimum width of tab |
| stl_customTabTextLayoutId | Layout ID defined custom tab. If you do not specify a layout, use the default tab |
| stl_customTabTextViewId | Text view ID in a custom tab layout. If you do not define with customTabTextLayoutId, does not work |
| stl_distributeEvenly | If set to true, each tab is given the same weight, default false |
| stl_clickable | If set to false, disable the selection of a tab click, default true |
| stl_titleOffset | If set to 'auto_center', the slide position of the tab in the middle it will keep to the center. If specify a dimension it will be offset from the left edge, default 24dp |
| stl_drawDecorationAfterTab | Draw the decoration(indicator and lines) after drawing of tab, default false |

*__Notes:__ Both 'stl_indicatorAlwaysInCenter' and 'stl_distributeEvenly' if it is set to true, it will throw UnsupportedOperationException.*

# How to customize the tab

The customization of tab There are three ways.

* Customize the attribute
* SmartTabLayout#setCustomTabView(int layoutResId, int textViewId)
* SmartTabLayout#setCustomTabView(TabProvider provider)

If set the TabProvider, can build a view for each tab.

```java

public class SmartTabLayout extends HorizontalScrollView {

    //...

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

    //...
}

```

# How to use the utility

Utility has two types available to suit the Android support library.

* utils-v4 library contains the PagerAdapter implementation class for _android.support.v4.app.Fragment_
* utils-v13 library contains the PagerAdapter implementation class for _android.app.Fragment_

The two libraries have different Android support libraries that depend,
but implemented functionality is the same.

## PagerAdapter for View-based Page

```java

ViewPagerItemAdapter adapter = new ViewPagerItemAdapter(ViewPagerItems.with(this)
        .add(R.string.title, R.layout.page)
        .add("title", R.layout.page)
        .create());

viewPager.setAdapter(adapter);

//...

public void onPageSelected(int position) {

  //.instantiateItem() from until .destroyItem() is called it will be able to get the View of page.
  View page = adapter.getPage(position);

}


```

## PagerAdapter for Fragment-based Page

Fragment-based PagerAdapter There are two implementations.
Please differences refer to the library documentation for Android.

* FragmentPagerItemAdapter extends FragmentPagerAdapter
* FragmentStatePagerItemAdapter extends FragmentStatePagerAdapter

```java

FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
        getSupportFragmentManager(), FragmentPagerItems.with(this)
        .add(R.string.title, PageFragment.class),
        .add(R.string.title, WithArgumentsPageFragment.class, new Bundler().putString("key", "value").get()),
        .add("title", PageFragment.class)
        .create());

viewPager.setAdapter(adapter);

//...

public void onPageSelected(int position) {

  //.instantiateItem() from until .destoryItem() is called it will be able to get the Fragment of page.
  Fragment page = adapter.getPage(position);

}

```

*__Notes:__ If using fragment inside a ViewPager, Must be use [Fragment#getChildFragmentManager()](http://developer.android.com/reference/android/support/v4/app/Fragment.html#getChildFragmentManager).*

# Apps Using SmartTabLayout

* [Qiitanium][qiitanium]
* [Ameba](https://play.google.com/store/apps/details?id=jp.ameba&hl=ja)

# Looking for iOS ?
 Check [WormTabStrip](https://github.com/EzimetYusup/WormTabStrip) out.
 
# LICENSE

```
Copyright (C) 2015 ogaclejapan
Copyright (C) 2013 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[demo1_gif]: https://raw.githubusercontent.com/ogaclejapan/SmartTabLayout/master/art/demo1.gif
[demo2_gif]: https://raw.githubusercontent.com/ogaclejapan/SmartTabLayout/master/art/demo2.gif
[demo3_gif]: https://raw.githubusercontent.com/ogaclejapan/SmartTabLayout/master/art/demo3.gif
[demo4_gif]: https://raw.githubusercontent.com/ogaclejapan/SmartTabLayout/master/art/demo4.gif
[demo5_gif]: https://raw.githubusercontent.com/ogaclejapan/SmartTabLayout/master/art/demo5.gif
[demo6_gif]: https://raw.githubusercontent.com/ogaclejapan/SmartTabLayout/master/art/demo6.gif
[demo7_gif]: https://raw.githubusercontent.com/ogaclejapan/SmartTabLayout/master/art/demo7.gif
[demo_app]: https://play.google.com/store/apps/details?id=com.ogaclejapan.smarttablayout.demo&referrer=utm_source%3Dgithub
[demo_icon]: https://raw.githubusercontent.com/ogaclejapan/SmartTabLayout/master/art/icon.png
[googleplay_store_badge]: http://www.android.com/images/brand/get_it_on_play_logo_large.png
[maven_central_badge_svg]: https://maven-badges.herokuapp.com/maven-central/com.ogaclejapan.smarttablayout/library/badge.svg?style=flat
[maven_central_badge_app]: https://maven-badges.herokuapp.com/maven-central/com.ogaclejapan.smarttablayout/library
[android_arsenal_badge_svg]: https://img.shields.io/badge/Android%20Arsenal-SmartTabLayout-brightgreen.svg?style=flat
[android_arsenal_badge_link]: http://android-arsenal.com/details/1/1683
[android_weekly_badge_svg]: https://img.shields.io/badge/AndroidWeekly-%23148-blue.svg
[android_weekly_badge_link]: http://androidweekly.net/issues/issue-148
[qiitanium]: https://github.com/ogaclejapan/Qiitanium
[google_slidingtabbasic]: https://github.com/googlesamples/android-SlidingTabsBasic
