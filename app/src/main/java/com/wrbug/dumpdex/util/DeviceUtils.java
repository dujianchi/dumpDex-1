package com.wrbug.dumpdex.util;

import android.os.Build;

/**
 * DeviceUtils
 *
 * @author suanlafen
 * @since 2018/4/8
 */
public class DeviceUtils {
    private static int sdkInit;

    static {
        sdkInit = Build.VERSION.SDK_INT;
    }

    public static boolean supportNativeHook() {
        return sdkInit >= 23;
    }

    public static boolean isOreo() {
        return sdkInit >= 26;
    }
}
