/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tv.twitch.android.mod.bridges.preference;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP_PREFIX;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.RestrictTo;

import tv.twitch.android.mod.bridges.ResourcesManager;

/**
 * A top-level container that represents a settings screen. This is the root component of your
 * {@link Preference} hierarchy. A {@link PreferenceFragmentCompat} points to an instance of this
 * class to show the preferences. To instantiate this class, use
 * {@link PreferenceManager#createPreferenceScreen(Context)}.
 *
 * <div class="special reference">
 * <h3>Developer Guides</h3>
 * <p>For information about building a settings screen using the AndroidX Preference library, see
 * <a href="{@docRoot}guide/topics/ui/settings.html">Settings</a>.</p>
 * </div>
 *
 */
public final class PreferenceScreen extends PreferenceGroup {

    private boolean mShouldUseGeneratedIds = true;

    /**
     * Do NOT use this constructor, use {@link PreferenceManager#createPreferenceScreen(Context)}.
     *
     * Used by Settings :)
     *
     * @hide
     */
    @RestrictTo(LIBRARY_GROUP_PREFIX)
    public PreferenceScreen(Context context, AttributeSet attrs) {
        super(context, attrs, TypedArrayUtils.getAttr(context, ResourcesManager.getAttrId("preferenceScreenStyle"),
                android.R.attr.preferenceScreenStyle));
    }

    @Override
    protected void onClick() {
        if (getIntent() != null || getFragment() != null || getPreferenceCount() == 0) {
            return;
        }
        final PreferenceManager.OnNavigateToScreenListener listener =
                getPreferenceManager().getOnNavigateToScreenListener();
        if (listener != null) {
            listener.onNavigateToScreen(this);
        }
    }

    @Override
    protected boolean isOnSameScreenAsChildren() {
        return false;
    }

    /**
     * See {@link #setShouldUseGeneratedIds(boolean)}
     *
     * @return {@code true} if the adapter should use the preference IDs generated by
     * {@link PreferenceGroup#addPreference(Preference)} as stable item IDs.
     */
    public boolean shouldUseGeneratedIds() {
        return mShouldUseGeneratedIds;
    }

    /**
     * Set whether the adapter created for this screen should attempt to use the preference IDs
     * generated by {@link PreferenceGroup#addPreference(Preference)} as stable item IDs. Setting
     * this to false can suppress unwanted animations if {@link Preference} objects are frequently
     * removed from and re-added to their containing {@link PreferenceGroup}.
     *
     * <p>This method may only be called when the preference screen is not attached to the
     * hierarchy.
     *
     * <p>Default value is {@code true}.
     *
     * @param shouldUseGeneratedIds {@code true} if the adapter should use the preference ID as a
     *                              stable ID, or {@code false} to disable the use of
     *                              stable IDs.
     */
    public void setShouldUseGeneratedIds(boolean shouldUseGeneratedIds) {
        if (isAttached()) {
            throw new IllegalStateException("Cannot change the usage of generated IDs while" +
                    " attached to the preference hierarchy");
        }
        mShouldUseGeneratedIds = shouldUseGeneratedIds;
    }
}
