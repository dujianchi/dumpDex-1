package com.wrbug.dumpdex;

import android.content.Context;
import android.os.Build;

import com.wrbug.dumpdex.dump.LowSdkDump;
import com.wrbug.dumpdex.dump.OreoDump;
import com.wrbug.dumpdex.util.DeviceUtils;

import java.io.File;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * XposedInit
 *
 * @author wrbug
 * @since 2018/3/20
 */
public class XposedInit implements IXposedHookLoadPackage {


    public static void log(String txt) {

        XposedBridge.log("dumpdex-> " + txt);
    }

    public static void log(Throwable t) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        XposedBridge.log(t);
    }

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) {
        PackerInfo.Type type = PackerInfo.find(lpparam);
        if (type == null) {
            return;
        }
        log("------ type name: " + type.getName());
        final String packageName = lpparam.packageName;
        if (lpparam.packageName.equals(packageName)) {
//            Context systemContext = (Context) XposedHelpers.callMethod(
//                    XposedHelpers.callStaticMethod(
//                            XposedHelpers.findClass(
//                                    "android.app.ActivityThread", lpparam.classLoader
//                            ), "currentActivityThread"
//                    ), "getSystemContext"
//            );
//            log("----- context: " + systemContext);
//            String path = systemContext.getFilesDir().getPath() + "/data/" + packageName + "/dump";
            String path = "/data/data/" + packageName + "/dump";
            log("----- path: " + path);
            File parent = new File(path);
            if (!parent.exists() || !parent.isDirectory()) {
                parent.mkdirs();
            }
            log("sdk version:" + Build.VERSION.SDK_INT);
            if (DeviceUtils.isOreo()) {
                OreoDump.init(lpparam);
            } else {
                LowSdkDump.init(lpparam, type);
            }

        }
    }
}
