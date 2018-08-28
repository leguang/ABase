package cn.itsite.abase;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import cn.itsite.abase.common.ascreen.AScreen;


/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class BaseApp extends MultiDexApplication {
    private static final String TAG = BaseApp.class.getSimpleName();
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        //屏幕适配初始化。
        AScreen.setDensity(this);
    }
}
