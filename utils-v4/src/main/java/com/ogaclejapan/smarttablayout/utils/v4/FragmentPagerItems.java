/**
 * Copyright (C) 2015 ogaclejapan
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
package com.ogaclejapan.smarttablayout.utils.v4;

import android.content.Context;
import android.os.Bundle;

import com.ogaclejapan.smarttablayout.utils.PagerItems;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

public class FragmentPagerItems extends PagerItems<FragmentPagerItem> {

  public FragmentPagerItems(Context context) {
    super(context);
  }

  public static Creator with(Context context) {
    return new Creator(context);
  }

  public static class Creator {

    private final FragmentPagerItems items;

    public Creator(Context context) {
      items = new FragmentPagerItems(context);
    }

    public Creator add(@StringRes int title, Class<? extends Fragment> clazz) {
      return add(FragmentPagerItem.of(items.getContext().getString(title), clazz));
    }

    public Creator add(@StringRes int title, Class<? extends Fragment> clazz, Bundle args) {
      return add(FragmentPagerItem.of(items.getContext().getString(title), clazz, args));
    }

    public Creator add(@StringRes int title, float width, Class<? extends Fragment> clazz) {
      return add(FragmentPagerItem.of(items.getContext().getString(title), width, clazz));
    }

    public Creator add(@StringRes int title, float width, Class<? extends Fragment> clazz,
        Bundle args) {
      return add(FragmentPagerItem.of(items.getContext().getString(title), width, clazz, args));
    }

    public Creator add(CharSequence title, Class<? extends Fragment> clazz) {
      return add(FragmentPagerItem.of(title, clazz));
    }

    public Creator add(CharSequence title, Class<? extends Fragment> clazz, Bundle args) {
      return add(FragmentPagerItem.of(title, clazz, args));
    }

    public Creator add(FragmentPagerItem item) {
      items.add(item);
      return this;
    }

    public FragmentPagerItems create() {
      return items;
    }

  }

}
