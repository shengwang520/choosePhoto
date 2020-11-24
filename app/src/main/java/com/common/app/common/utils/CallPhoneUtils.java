package com.common.app.common.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by 圣王 on 2015/6/17 0017.
 */
public class CallPhoneUtils {

    public static void startCallPhone(Context context, String phone) {
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        phoneIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(phoneIntent);
    }
}
