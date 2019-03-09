package com.ogaclejapan.smarttablayout.demo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class DemoTabWithNotificationMarkActivity extends AppCompatActivity implements
    SmartTabLayout.TabProvider {

  private static final String KEY_DEMO = "demo";

  public static void startActivity(Context context, Demo demo) {
    Intent intent = new Intent(context, DemoTabWithNotificationMarkActivity.class);
    intent.putExtra(KEY_DEMO, demo.name());
    context.startActivity(intent);
  }

  private Random random = new Random(System.currentTimeMillis());

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_demo_tab_with_notification_mark);

    final Demo demo = getDemo();

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle(demo.titleResId);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    ViewGroup tab = (ViewGroup) findViewById(R.id.tab);
    tab.addView(LayoutInflater.from(this).inflate(demo.layoutResId, tab, false));

    ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
    final SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
    viewPagerTab.setCustomTabView(this);

    FragmentPagerItems pages = new FragmentPagerItems(this);
    for (int titleResId : demo.tabs()) {
      pages.add(FragmentPagerItem.of(getString(titleResId), DemoFragment.class));
    }

    FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
        getSupportFragmentManager(), pages);

    viewPager.setAdapter(adapter);
    viewPagerTab.setViewPager(viewPager);

    viewPagerTab.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
      @Override
      public void onPageSelected(int position) {
        super.onPageSelected(position);
        View tab = viewPagerTab.getTabAt(position);
        View mark = tab.findViewById(R.id.custom_tab_notification_mark);
        mark.setVisibility(View.GONE);
      }
    });

    findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int position = Math.abs(random.nextInt()) % demo.tabs().length;
        View tab = viewPagerTab.getTabAt(position);
        View mark = tab.findViewById(R.id.custom_tab_notification_mark);
        mark.setVisibility(View.VISIBLE);
      }
    });

  }

  @Override
  public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
    LayoutInflater inflater = LayoutInflater.from(container.getContext());
    Resources res = container.getContext().getResources();
    View tab = inflater.inflate(R.layout.custom_tab_icon_and_notification_mark, container, false);
    View mark = tab.findViewById(R.id.custom_tab_notification_mark);
    mark.setVisibility(View.GONE);
    ImageView icon = (ImageView) tab.findViewById(R.id.custom_tab_icon);
    switch (position) {
      case 0:
        icon.setImageDrawable(res.getDrawable(R.drawable.ic_home_white_24dp));
        break;
      case 1:
        icon.setImageDrawable(res.getDrawable(R.drawable.ic_search_white_24dp));
        break;
      case 2:
        icon.setImageDrawable(res.getDrawable(R.drawable.ic_person_white_24dp));
        break;
      default:
        throw new IllegalStateException("Invalid position: " + position);
    }
    return tab;
  }

  private Demo getDemo() {
    return Demo.valueOf(getIntent().getStringExtra(KEY_DEMO));
  }
}
