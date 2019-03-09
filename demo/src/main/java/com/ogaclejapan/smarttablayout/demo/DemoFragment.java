package com.ogaclejapan.smarttablayout.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DemoFragment extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_demo, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    int position = FragmentPagerItem.getPosition(getArguments());
    TextView title = (TextView) view.findViewById(R.id.item_title);
    title.setText(String.valueOf(position));
  }

}
