package cn.itsite.abase.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class ToastUtils {
    public static Toast mToast;

    public static void showToast(Context mContext, CharSequence msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }

    public static void showToast(Context mContext, @StringRes int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(resId);
        mToast.show();
    }
}
