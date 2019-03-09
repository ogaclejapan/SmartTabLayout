package com.ogaclejapan.smarttablayout.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AbsListView.OnItemClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ListView listView = (ListView) findViewById(R.id.list);
    listView.setOnItemClickListener(this);

    ArrayAdapter<String> demoAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1);

    for (Demo demo : Demo.values()) {
      demoAdapter.add(getString(demo.titleResId));
    }

    listView.setAdapter(demoAdapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_github:
        openGitHub();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    Demo demo = Demo.values()[position];
    demo.startActivity(this);
  }

  private void openGitHub() {
    Uri uri = Uri.parse(getString(R.string.app_github_url));
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    startActivity(intent);
  }

}
