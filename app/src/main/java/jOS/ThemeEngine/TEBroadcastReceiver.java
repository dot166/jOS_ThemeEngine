/*
 * Copyright (C) 2024 ._______166
 */

package jOS.ThemeEngine;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * This class detects the {@link Intent#ACTION_MY_PACKAGE_REPLACED} broadcast intent when this App
 * package has been replaced by a newer version of the same package. This class also detects
 * {@link Intent#ACTION_BOOT_COMPLETED} and {@link Intent#ACTION_USER_INITIALIZE} broadcast intent.
 *
 * If this App has already been installed in the system image and a new version of this App has
 * been installed, {@link Intent#ACTION_MY_PACKAGE_REPLACED} is received by this receiver and it
 * will hide the launcher activity's icon.
 *
 * If this App has already been installed in the data partition and a new version of this App has
 * been installed, {@link Intent#ACTION_MY_PACKAGE_REPLACED} is received by this receiver but it
 * will not hide the launcher activity's icon, and the icon will appear on the launcher.
 *
 * If this App hasn't been installed yet and has been newly installed, no
 * {@link Intent#ACTION_MY_PACKAGE_REPLACED} will be sent and the launcher activity's icon will appear
 * on the launcher.
 *
 * When the device has been booted, {@link Intent#ACTION_BOOT_COMPLETED} is received by this
 * receiver and it checks whether the launcher activity's icon should be appeared or not on the launcher
 * depending on which partition this App is installed.
 *
 * When a multiuser account has been created, {@link Intent#ACTION_USER_INITIALIZE} is received
 * by this receiver and it checks the whether the launcher activity's icon should be appeared or not on
 * the launcher depending on which partition this App is installed.
 */
public final class TEBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = TEBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (shouldHandleThisIntent(intent, context)) {
            if (isInSystemImage(context)) {
                disableActivity(context, LauncherActivity.class);
            } else {
                Log.i(TAG, "This package isn't in system image: " + context.getPackageName());
            }
        }
    }

    private static boolean shouldHandleThisIntent(final Intent intent, final Context context) {
        final String action = intent.getAction();
        if (Intent.ACTION_MY_PACKAGE_REPLACED.equals(action)) {
            Log.i(TAG, "Package has been replaced: " + context.getPackageName());
            return true;
        } else if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            Log.i(TAG, "Boot has been completed");
            return true;
        } else if (Intent.ACTION_USER_INITIALIZE.equals(intent)) {
            Log.i(TAG, "User initialize");
            return true;
        }
        return false;
    }

    /**
     * Disable an activity of the specified package. Disabling an activity will also hide its
     * icon from the launcher.
     *
     * @param context package context of an activity to be disabled
     * @param activityClass activity class to be disabled
     */
    private static void disableActivity(final Context context,
                                        final Class<? extends Activity> activityClass) {
        final ComponentName activityComponent = new ComponentName(context, activityClass);
        final PackageManager pm = context.getPackageManager();
        final int activityComponentState = pm.getComponentEnabledSetting(activityComponent);
        if (activityComponentState == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
            // This activity is already disabled.
            Log.i(TAG, "Activity has already been disabled: " + activityComponent);
            return;
        }
        // Disabling an activity will also hide its icon from the launcher.
        pm.setComponentEnabledSetting(activityComponent,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        Log.i(TAG, "Disable activity: " + activityComponent);
    }

    private static boolean isInSystemImage(final Context context) {
        final ApplicationInfo appInfo = context.getApplicationInfo();
        return (appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }
}
