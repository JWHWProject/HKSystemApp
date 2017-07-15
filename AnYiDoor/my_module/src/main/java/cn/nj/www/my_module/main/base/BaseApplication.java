package cn.nj.www.my_module.main.base;

import android.app.Activity;
import android.app.Application;

import org.apache.http.client.CookieStore;

import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.constant.Global;
import cn.nj.www.my_module.tools.GeneralUtils;

/**
 * <baseapplication>
 *
 */
public class BaseApplication extends Application {
    /**
     * 导航栏高度
     */
    public static int statusBarHeight = 0;

    /**
     * cookie缓存
     */
    public static CookieStore cookieStore = null;

    /**
     * app实例
     */
    private static BaseApplication sInstance;

    /**
     * 本地activity
     */
    public static List<Activity> activitys = new ArrayList<Activity>();

    /**
     * 当前avtivity
     */
    public static String currentActivity = "";

    /**
     * Fragment实例
     */
    public static String modelName = "";

    /**
     * 设备号
     */
    public static String DEVICE_TOKEN = "";


    /**
     * url Tag
     */
    public static String urlTag = "";

    public static String isForced = "0";


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Global.saveOpenApp(true);
        DEVICE_TOKEN = GeneralUtils.getDeviceId();
    }

    public static synchronized BaseApplication getInstance() {
        return sInstance;
    }

    /**
     * <删除>
     */
    public void deleteActivity(Activity activity) {
        if (activity != null) {
            activitys.remove(activity);
            activity.finish();
        }
    }

    /**
     * <添加activity>
     */
    public void addActivity(Activity activity) {
        activitys.add(activity);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Global.logoutApplication();
    }

}
