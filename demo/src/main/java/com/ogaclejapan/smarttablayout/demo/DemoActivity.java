package com.ogaclejapan.smarttablayout.demo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class DemoActivity extends AppCompatActivity {

    private static final String KEY_DEMO = "demo";

    public static void startActivity(Context context, Demo demo) {
        Intent intent = new Intent(context, DemoActivity.class);
        intent.putExtra(KEY_DEMO, demo.name());
        context.startActivity(intent);
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
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        demo.setup(viewPagerTab);

        // Sample Usage for setGradientIndicator
//        viewPagerTab.setGradientIndicator(Color.rgb(243, 22, 42), Color.rgb(10, 32, 210));

        FragmentPagerItems pages = new FragmentPagerItems(this);
        for (int titleResId : demo.tabs()) {
            pages.add(FragmentPagerItem.of(getString(titleResId), DemoFragment.class));
        }

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), pages);

        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);

    }

    private Demo getDemo() {
        return Demo.valueOf(getIntent().getStringExtra(KEY_DEMO));
    }
}
