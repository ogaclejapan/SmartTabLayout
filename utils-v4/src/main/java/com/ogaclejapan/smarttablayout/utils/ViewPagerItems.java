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
package com.ogaclejapan.smarttablayout.utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;

public class ViewPagerItems extends PagerItems<ViewPagerItem> {

  public ViewPagerItems(Context context) {
    super(context);
  }

  public static Creator with(Context context) {
    return new Creator(context);
  }

  public static class Creator {

    private final ViewPagerItems items;

    public Creator(Context context) {
      items = new ViewPagerItems(context);
    }

    public Creator add(@StringRes int title, @LayoutRes int resource) {
      return add(ViewPagerItem.of(items.getContext().getString(title), resource));
    }

    public Creator add(@StringRes int title, float width, @LayoutRes int resource) {
      return add(ViewPagerItem.of(items.getContext().getString(title), width, resource));
    }

    public Creator add(CharSequence title, @LayoutRes int resource) {
      return add(ViewPagerItem.of(title, resource));
    }

    public Creator add(ViewPagerItem item) {
      items.add(item);
      return this;
    }

    public ViewPagerItems create() {
      return items;
    }

  }

}
