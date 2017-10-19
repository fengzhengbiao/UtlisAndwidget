package com.jokerfishlib.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;

import java.util.Iterator;
import java.util.List;

/**
 * Created by JokerFish on 2017/10/9.
 */

public class ActivityUtil {
    /**
     * 获取打开设置的意图
     * @param context
     * @return
     */
    public static Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }

    /**
     * 根据应用包名获取启动的activity名字
     * @param context
     * @param packageName
     * @return
     */
    public static String getLauncherActivityNameByPackageName(Context context, String packageName) {
        String className = null;
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);//android.intent.action.MAIN
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);//android.intent.category.LAUNCHER
        resolveIntent.setPackage(packageName);
        List<ResolveInfo> resolveinfoList = context.getPackageManager().queryIntentActivities(resolveIntent, 0);
        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            className = resolveinfo.activityInfo.name;
        }
        return className;
    }




    public static String getLauncherClassName(Context paramContext) {
        String clazzName = "";
        PackageManager localPackageManager = paramContext.getPackageManager();
        Intent localIntent = new Intent("android.intent.action.MAIN");
        localIntent.addCategory("android.intent.category.LAUNCHER");
        try {
            Iterator localIterator = localPackageManager.queryIntentActivities(localIntent, 0).iterator();
            while (localIterator.hasNext()) {
                ResolveInfo localResolveInfo = (ResolveInfo) localIterator.next();
                if (!localResolveInfo.activityInfo.applicationInfo.packageName.equalsIgnoreCase(paramContext.getPackageName()))
                    continue;
                clazzName = localResolveInfo.activityInfo.name;
                return clazzName;
            }
        } catch (Exception e) {
            return clazzName;
        }
        return clazzName;
    }


}
