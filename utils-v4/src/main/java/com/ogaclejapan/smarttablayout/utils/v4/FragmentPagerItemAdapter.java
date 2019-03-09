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

import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentPagerItemAdapter extends FragmentPagerAdapter {

  private final FragmentPagerItems pages;
  private final SparseArrayCompat<WeakReference<Fragment>> holder;

  public FragmentPagerItemAdapter(FragmentManager fm, FragmentPagerItems pages) {
    super(fm);
    this.pages = pages;
    this.holder = new SparseArrayCompat<>(pages.size());
  }

  @Override
  public int getCount() {
    return pages.size();
  }

  @Override
  public Fragment getItem(int position) {
    return getPagerItem(position).instantiate(pages.getContext(), position);
  }

  @NonNull
  @Override
  public Object instantiateItem(@NonNull ViewGroup container, int position) {
    Object item = super.instantiateItem(container, position);
    if (item instanceof Fragment) {
      holder.put(position, new WeakReference<Fragment>((Fragment) item));
    }
    return item;
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    holder.remove(position);
    super.destroyItem(container, position, object);
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return getPagerItem(position).getTitle();
  }

  @Override
  public float getPageWidth(int position) {
    return super.getPageWidth(position);
  }

  public Fragment getPage(int position) {
    final WeakReference<Fragment> weakRefItem = holder.get(position);
    return (weakRefItem != null) ? weakRefItem.get() : null;
  }

  protected FragmentPagerItem getPagerItem(int position) {
    return pages.get(position);
  }

}
