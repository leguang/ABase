package cn.itsite.abase.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

//打开或关闭软键盘
public class KeyboardUtils {

    private KeyboardUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 打开软键盘
     *
     * @param view     输入框
     * @param mContext 上下文
     */
    public static void showKeybord(View view, Context mContext) {
        if (mContext != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        }
    }

    /**
     * 关闭软键盘
     *
     * @param v        获取Token的View
     * @param mContext 上下文
     */
    public static void hideKeybord(View v, Context mContext) {
        if (mContext != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        }
    }
}
