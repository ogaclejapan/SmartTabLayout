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

import android.annotation.TargetApi;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;

public class Bundler {

  private final Bundle bundle;

  /**
   * Constructs a new, empty Bundle.
   */
  public Bundler() {
    this(null);
  }

  private Bundler(Bundle b) {
    bundle = (b == null) ? new Bundle() : new Bundle(b);
  }

  /**
   * Constructs a Bundle containing a copy of the mappings from the given
   * Bundle.
   *
   * @param b a Bundle to be copied.
   */
  public static Bundler of(Bundle b) {
    return new Bundler(b);
  }

  /**
   * Inserts all mappings from the given Bundle into this Bundle.
   *
   * @param bundle a Bundle
   */
  public Bundler putAll(Bundle bundle) {
    this.bundle.putAll(bundle);
    return this;
  }

  /**
   * Inserts a byte value into the mapping of this Bundle, replacing
   * any existing value for the given key.
   *
   * @param key   a String, or null
   * @param value a byte
   */
  public Bundler putByte(String key, byte value) {
    bundle.putByte(key, value);
    return this;
  }

  /**
   * Inserts a char value into the mapping of this Bundle, replacing
   * any existing value for the given key.
   *
   * @param key   a String, or null
   * @param value a char, or null
   */
  public Bundler putChar(String key, char value) {
    bundle.putChar(key, value);
    return this;
  }

  /**
   * Inserts a short value into the mapping of this Bundle, replacing
   * any existing value for the given key.
   *
   * @param key   a String, or null
   * @param value a short
   */
  public Bundler putShort(String key, short value) {
    bundle.putShort(key, value);
    return this;
  }

  /**
   * Inserts a float value into the mapping of this Bundle, replacing
   * any existing value for the given key.
   *
   * @param key   a String, or null
   * @param value a float
   */
  public Bundler putFloat(String key, float value) {
    bundle.putFloat(key, value);
    return this;
  }

  /**
   * Inserts a CharSequence value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * @param key   a String, or null
   * @param value a CharSequence, or null
   */
  public Bundler putCharSequence(String key, CharSequence value) {
    bundle.putCharSequence(key, value);
    return this;
  }

  /**
   * Inserts a Parcelable value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * @param key   a String, or null
   * @param value a Parcelable object, or null
   */
  public Bundler putParcelable(String key, Parcelable value) {
    bundle.putParcelable(key, value);
    return this;
  }

  /**
   * Inserts a Size value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * @param key   a String, or null
   * @param value a Size object, or null
   */
  @TargetApi(21)
  public Bundler putSize(String key, Size value) {
    bundle.putSize(key, value);
    return this;
  }

  /**
   * Inserts a SizeF value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * @param key   a String, or null
   * @param value a SizeF object, or null
   */
  @TargetApi(21)
  public Bundler putSizeF(String key, SizeF value) {
    bundle.putSizeF(key, value);
    return this;
  }

  /**
   * Inserts an array of Parcelable values into the mapping of this Bundle,
   * replacing any existing value for the given key.  Either key or value may
   * be null.
   *
   * @param key   a String, or null
   * @param value an array of Parcelable objects, or null
   */
  public Bundler putParcelableArray(String key, Parcelable[] value) {
    bundle.putParcelableArray(key, value);
    return this;
  }

  /**
   * Inserts a List of Parcelable values into the mapping of this Bundle,
   * replacing any existing value for the given key.  Either key or value may
   * be null.
   *
   * @param key   a String, or null
   * @param value an ArrayList of Parcelable objects, or null
   */
  public Bundler putParcelableArrayList(String key,
      ArrayList<? extends Parcelable> value) {
    bundle.putParcelableArrayList(key, value);
    return this;
  }

  /**
   * Inserts a SparceArray of Parcelable values into the mapping of this
   * Bundle, replacing any existing value for the given key.  Either key
   * or value may be null.
   *
   * @param key   a String, or null
   * @param value a SparseArray of Parcelable objects, or null
   */
  public Bundler putSparseParcelableArray(String key,
      SparseArray<? extends Parcelable> value) {
    bundle.putSparseParcelableArray(key, value);
    return this;
  }

  /**
   * Inserts an ArrayList<Integer> value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * @param key   a String, or null
   * @param value an ArrayList<Integer> object, or null
   */
  public Bundler putIntegerArrayList(String key, ArrayList<Integer> value) {
    bundle.putIntegerArrayList(key, value);
    return this;
  }

  /**
   * Inserts an ArrayList<String> value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * @param key   a String, or null
   * @param value an ArrayList<String> object, or null
   */
  public Bundler putStringArrayList(String key, ArrayList<String> value) {
    bundle.putStringArrayList(key, value);
    return this;
  }

  /**
   * Inserts an ArrayList<CharSequence> value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * @param key   a String, or null
   * @param value an ArrayList<CharSequence> object, or null
   */
  @TargetApi(8)
  public Bundler putCharSequenceArrayList(String key, ArrayList<CharSequence> value) {
    bundle.putCharSequenceArrayList(key, value);
    return this;
  }

  /**
   * Inserts a Serializable value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * @param key   a String, or null
   * @param value a Serializable object, or null
   */
  public Bundler putSerializable(String key, Serializable value) {
    bundle.putSerializable(key, value);
    return this;
  }

  /**
   * Inserts a byte array value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * @param key   a String, or null
   * @param value a byte array object, or null
   */
  public Bundler putByteArray(String key, byte[] value) {
    bundle.putByteArray(key, value);
    return this;
  }

  /**
   * Inserts a short array value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * @param key   a String, or null
   * @param value a short array object, or null
   */
  public Bundler putShortArray(String key, short[] value) {
    bundle.putShortArray(key, value);
    return this;
  }

  /**
   * Inserts a char array value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * @param key   a String, or null
   * @param value a char array object, or null
   */
  public Bundler putCharArray(String key, char[] value) {
    bundle.putCharArray(key, value);
    return this;
  }

  /**
   * Inserts a float array value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * @param key   a String, or null
   * @param value a float array object, or null
   */
  public Bundler putFloatArray(String key, float[] value) {
    bundle.putFloatArray(key, value);
    return this;
  }

  /**
   * Inserts a CharSequence array value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * @param key   a String, or null
   * @param value a CharSequence array object, or null
   */
  @TargetApi(8)
  public Bundler putCharSequenceArray(String key, CharSequence[] value) {
    bundle.putCharSequenceArray(key, value);
    return this;
  }

  /**
   * Inserts a Bundle value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * @param key   a String, or null
   * @param value a Bundle object, or null
   */
  public Bundler putBundle(String key, Bundle value) {
    bundle.putBundle(key, value);
    return this;
  }

  /**
   * Inserts an {@link android.os.IBinder} value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * <p class="note">You should be very careful when using this function.  In many
   * places where Bundles are used (such as inside of Intent objects), the Bundle
   * can live longer inside of another process than the process that had originally
   * created it.  In that case, the IBinder you supply here will become invalid
   * when your process goes away, and no longer usable, even if a new process is
   * created for you later on.</p>
   *
   * @param key   a String, or null
   * @param value an IBinder object, or null
   */
  @TargetApi(18)
  public Bundler putBinder(String key, IBinder value) {
    bundle.putBinder(key, value);
    return this;
  }

  /**
   * Inserts a Boolean value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * @param key   a String, or null
   * @param value a Boolean, or null
   */
  public Bundler putBoolean(String key, boolean value) {
    bundle.putBoolean(key, value);
    return this;
  }

  /**
   * Inserts an int value into the mapping of this Bundle, replacing
   * any existing value for the given key.
   *
   * @param key   a String, or null
   * @param value an int, or null
   */
  public Bundler putInt(String key, int value) {
    bundle.putInt(key, value);
    return this;
  }

  /**
   * Inserts a long value into the mapping of this Bundle, replacing
   * any existing value for the given key.
   *
   * @param key   a String, or null
   * @param value a long
   */
  public Bundler putLong(String key, long value) {
    bundle.putLong(key, value);
    return this;
  }

  /**
   * Inserts a double value into the mapping of this Bundle, replacing
   * any existing value for the given key.
   *
   * @param key   a String, or null
   * @param value a double
   */
  public Bundler putDouble(String key, double value) {
    bundle.putDouble(key, value);
    return this;
  }

  /**
   * Inserts a String value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * @param key   a String, or null
   * @param value a String, or null
   */
  public Bundler putString(String key, String value) {
    bundle.putString(key, value);
    return this;
  }

  /**
   * Inserts a boolean array value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * @param key   a String, or null
   * @param value a boolean array object, or null
   */
  public Bundler putBooleanArray(String key, boolean[] value) {
    bundle.putBooleanArray(key, value);
    return this;
  }

  /**
   * Inserts an int array value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * @param key   a String, or null
   * @param value an int array object, or null
   */
  public Bundler putIntArray(String key, int[] value) {
    bundle.putIntArray(key, value);
    return this;
  }

  /**
   * Inserts a long array value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * @param key   a String, or null
   * @param value a long array object, or null
   */
  public Bundler putLongArray(String key, long[] value) {
    bundle.putLongArray(key, value);
    return this;
  }

  /**
   * Inserts a double array value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * @param key   a String, or null
   * @param value a double array object, or null
   */
  public Bundler putDoubleArray(String key, double[] value) {
    bundle.putDoubleArray(key, value);
    return this;
  }

  /**
   * Inserts a String array value into the mapping of this Bundle, replacing
   * any existing value for the given key.  Either key or value may be null.
   *
   * @param key   a String, or null
   * @param value a String array object, or null
   */
  public Bundler putStringArray(String key, String[] value) {
    bundle.putStringArray(key, value);
    return this;
  }

  /**
   * Get the bundle.
   *
   * @return a bundle
   */
  public Bundle get() {
    return bundle;
  }

  /**
   * Set the argument of Fragment.
   *
   * @param fragment a fragment
   * @return a fragment
   */
  public <T extends Fragment> T into(T fragment) {
    fragment.setArguments(get());
    return fragment;
  }

}
