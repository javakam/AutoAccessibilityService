package com.ando.autoservice;

import android.app.Application;
import android.provider.Settings;

/**
 * Title: AutoApplication
 * <p>
 * Description:
 * </p>
 *
 * @author Changbao
 * @date 2020/4/27  14:41
 */
public class AutoApplication extends Application {

    private static  AutoApplication INSTANCE;
    public static AutoApplication getInstance() {
        return INSTANCE;
    }

    private static final String enabledServicesBuilder = "com.ando.autoservice/com.ando.autoservice.AutoClickService";
    private boolean accessibilityEnabled;

    public void openPermission() {
        accessibilityEnabled = true;
        Settings.Secure.putString(getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, enabledServicesBuilder);
        Settings.Secure.putInt(getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED, accessibilityEnabled ? 1 : 0);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}
