package com.jokerfishlib.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by JokerFish on 2017/8/28.
 * 线程工具类
 */

public class ThreadUtil {
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static ExecutorService sExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() < 2 ? 2 * Runtime.getRuntime().availableProcessors() : 4);

    /**
     * 运行在子线程
     *
     * @param runnable
     */
    public static void runOnSubThread(Runnable runnable) {
        sExecutor.execute(runnable);
    }

    public static void runOnUIThread(Runnable runnable, long delayMillis) {
        sHandler.postDelayed(runnable, delayMillis);
    }
}
