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
package com.sugar.apptest.dexterpermission

import com.sugar.apptest.dexterpermission.DexterPermissionsUtil.CallBack
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener

class SamplePermissionListener internal constructor(private val mCallBack: CallBack) :
    BasePermissionListener() {
    override fun onPermissionGranted(response: PermissionGrantedResponse) {
        mCallBack.showPermissionGranted(response.permissionName)
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse) {
        mCallBack.showPermissionDenied(response.permissionName, response.isPermanentlyDenied)
    }
}