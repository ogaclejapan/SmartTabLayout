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
package com.ogaclejapan.smarttablayout.utils.v4;

import com.ogaclejapan.smarttablayout.utils.PagerItem;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class FragmentPagerItem extends PagerItem {

    private static final String TAG = "FragmentPagerItem";
    private static final String KEY_POSITION = TAG + ":Position";

    private final String mClassName;
    private final Bundle mExtras;

    protected FragmentPagerItem(CharSequence title, float width, String className, Bundle extras) {
        super(title, width);
        mClassName = className;
        mExtras = extras;
    }

    public static FragmentPagerItem of(CharSequence title, Class<? extends Fragment> clazz) {
        return of(title, DEFAULT_WIDTH, clazz);
    }

    public static FragmentPagerItem of(CharSequence title, Class<? extends Fragment> clazz, Bundle extras) {
        return of(title, DEFAULT_WIDTH, clazz, extras);
    }

    public static FragmentPagerItem of(CharSequence title, float width, Class<? extends Fragment> clazz) {
        return of(title, width, clazz, new Bundle());
    }

    public static FragmentPagerItem of(CharSequence title, float width, Class<? extends Fragment> clazz, Bundle extras) {
        return new FragmentPagerItem(title, width, clazz.getName(), extras);
    }

    public static boolean hasPosition(Bundle extras) {
        return extras != null && extras.containsKey(KEY_POSITION);
    }

    public static int getPosition(Bundle extras) {
        return (hasPosition(extras)) ? extras.getInt(KEY_POSITION) : 0;
    }

    static void setPosition(Bundle extras, int position) {
        extras.putInt(KEY_POSITION, position);
    }

    public Fragment instantiate(Context context, int position) {
        setPosition(mExtras, position);
        return Fragment.instantiate(context, mClassName, mExtras);
    }

}
