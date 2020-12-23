/*
 * Copyright (C) 2015 Karumi.
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

package tv.twitch.android.mod.bridges.dexter.listener.multi;

import java.util.List;

import tv.twitch.android.mod.bridges.dexter.MultiplePermissionsReport;
import tv.twitch.android.mod.bridges.dexter.PermissionToken;
import tv.twitch.android.mod.bridges.dexter.listener.PermissionRequest;

/**
 * Interface that listens to updates to the permission requests
 */
public interface MultiplePermissionsListener {

  /**
   * Method called when all permissions has been completely checked
   *
   * @param report In detail report with all the permissions that has been denied and granted
   */
  void onPermissionsChecked(MultiplePermissionsReport report);

  /**
   * Method called whenever Android asks the application to inform the user of the need for the
   * requested permissions. The request process won't continue until the token is properly used
   *
   * @param permissions The permissions that has been requested. Collections of values found in
   * {@link android.Manifest.permission}
   * @param token Token used to continue or cancel the permission request process. The permission
   * request process will remain blocked until one of the token methods is called
   */
  void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
      PermissionToken token);
}
