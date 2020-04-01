package cn.itsite.abase;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

/**
 * @version v0.0.0
 * @Author leguang
 * @E-mail langmanleguang@qq.com
 * @Blog https://github.com/leguang
 * @Time 2016/4/1/0001 17:21
 * Description:
 */
public class BaseApplication extends MultiDexApplication {
    public static final String TAG = BaseApplication.class.getSimpleName();
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
