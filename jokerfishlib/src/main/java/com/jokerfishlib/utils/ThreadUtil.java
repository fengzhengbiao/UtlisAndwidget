package com.jokerfishlib.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by JokerFish on 2017/8/28.
 * 线程工具类
 */

public class ThreadUtil {
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static Executor sExecutor = Executors.newSingleThreadExecutor();

    /**
     * 运行在子线程
     * @param runnable
     */
    public static void runOnSubThread(Runnable runnable){
        sExecutor.execute(runnable);
    }

    public static void runOnUIThread(Runnable runnable,long delayMillis){
        sHandler.postDelayed(runnable,delayMillis);
    }
}
