package cn.itsite.abase.common;

import android.view.View;

import java.util.Calendar;

/**
 * Created by leguang on 2016/8/15 0015.
 * 防止多次点击，或者抖动点击
 */
public abstract class NoMultiClickListener implements View.OnClickListener {
    public static final int MIN_DELAY_TIME = 618;
    private long lastClickTime = 0;

    @Override
    public void onClick(View view) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoMultiClick(view);
        }
    }

    public abstract void onNoMultiClick(View view);
}