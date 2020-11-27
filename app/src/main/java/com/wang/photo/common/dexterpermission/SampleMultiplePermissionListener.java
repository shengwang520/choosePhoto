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

package com.wang.photo.common.dexterpermission;

import android.text.TextUtils;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;

public class SampleMultiplePermissionListener extends BaseMultiplePermissionsListener {
    private DexterPermissionsUtil.CallBack mCallBack;

    SampleMultiplePermissionListener(DexterPermissionsUtil.CallBack
                                             callBack) {
        this.mCallBack = callBack;
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        if (report.areAllPermissionsGranted()) {
            mCallBack.showPermissionGranted(TextUtils.join(",", report.getGrantedPermissionResponses()));
        } else {
            mCallBack.showPermissionDenied(TextUtils.join(",", report.getDeniedPermissionResponses()), false);
        }
    }

}
