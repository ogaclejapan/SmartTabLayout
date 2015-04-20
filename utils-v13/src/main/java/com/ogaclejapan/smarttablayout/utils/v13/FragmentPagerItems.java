/**
 * Copyright (C) 2015 ogaclejapan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ogaclejapan.smarttablayout.utils.v13;

import com.ogaclejapan.smarttablayout.utils.PagerItems;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;

public class FragmentPagerItems extends PagerItems<FragmentPagerItem> {

    public FragmentPagerItems(Context context) {
        super(context);
    }

    public static Creator with(Context context) {
        return new Creator(context);
    }

    public static class Creator {

        private final FragmentPagerItems mItems;

        public Creator(Context context) {
            mItems = new FragmentPagerItems(context);
        }

        public Creator add(@StringRes int title, Class<? extends Fragment> clazz) {
            mItems.add(FragmentPagerItem.of(mItems.getContext().getString(title), clazz));
            return this;
        }

        public Creator add(@StringRes int title, Class<? extends Fragment> clazz, Bundle extras) {
            mItems.add(FragmentPagerItem.of(mItems.getContext().getString(title), clazz, extras));
            return this;
        }

        public Creator add(@StringRes int title, float width, Class<? extends Fragment> clazz) {
            mItems.add(FragmentPagerItem.of(mItems.getContext().getString(title), width, clazz));
            return this;
        }

        public Creator add(@StringRes int title, float width, Class<? extends Fragment> clazz, Bundle extras) {
            mItems.add(FragmentPagerItem.of(mItems.getContext().getString(title), width, clazz, extras));
            return this;
        }

        public Creator add(String title, Class<? extends Fragment> clazz) {
            mItems.add(FragmentPagerItem.of(title, clazz));
            return this;
        }

        public Creator add(FragmentPagerItem item) {
            mItems.add(item);
            return this;
        }

        public FragmentPagerItems create() {
            return mItems;
        }

    }
}
