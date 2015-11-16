package com.ogaclejapan.smarttablayout.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

/**
 * Created by hesk on 29/10/15.
 */
public class DemoDynamicTabs extends AppCompatActivity {
    private static final String KEY_DEMO = "demo";
    private FragmentPagerItems pages;
    private SmartTabLayout viewPagerTab;

    public static void startActivity(Context context, Demo demo) {
        Intent intent = new Intent(context, DemoDynamicTabs.class);
        intent.putExtra(KEY_DEMO, demo.name());
        context.startActivity(intent);
    }


    private Demo getDemo() {
        return Demo.valueOf(getIntent().getStringExtra(KEY_DEMO));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        Demo demo = getDemo();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(demo.titleResId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewGroup tab = (ViewGroup) findViewById(R.id.tab);
        tab.addView(LayoutInflater.from(this).inflate(demo.layoutResId, tab, false));

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        demo.setup(viewPagerTab);

        pages = new FragmentPagerItems(this);
        for (int titleResId : demo.tabs()) {
            pages.add(FragmentPagerItem.of(getString(titleResId), DemoFragment.class));
        }

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), pages);

        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);

    }

    /**
     * need some more work to make it happens on dynamics
     */
    private void add() {
        if (pages == null) return;
        String name = (int) (pages.size() + 1) + "newTabs";
        pages.add(FragmentPagerItem.of(name, DemoFragment.class));
        viewPagerTab.notifyAll();
    }

    /**
     * dynamic remove tabs
     */
    private void remove() {
        if (pages == null) return;
        if (pages.size() > Demo.tab3().length) {
            pages.remove(pages.size() - 1);
            viewPagerTab.notifyAll();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dy, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_tab:
                add();
                return true;
            case R.id.remove_tab:
                remove();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
