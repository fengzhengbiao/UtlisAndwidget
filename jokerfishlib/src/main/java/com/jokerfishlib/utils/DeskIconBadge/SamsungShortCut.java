package com.jokerfishlib.utils.DeskIconBadge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by JokerFish on 2017/10/9.
 */

public class SamsungShortCut implements IShortCut {
    @Override
    public void showShortCut(Context context, int num) {
        if (num < 1) {
            num = 0;
        } else if (num > 99) {
            num = 99;
        }
        Activity activity=null;
//        String activityName = activity.getLaunchActivityName(context);
        Intent localIntent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        localIntent.putExtra("badge_count", num);
        localIntent.putExtra("badge_count_package_name", context.getPackageName());
//        localIntent.putExtra("badge_count_class_name", activityName);
        context.sendBroadcast(localIntent);
    }
}
