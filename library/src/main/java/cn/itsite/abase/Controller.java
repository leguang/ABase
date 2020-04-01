package cn.itsite.abase;

import android.content.Context;
import android.view.View;

/**
 * @version v0.0.0
 * @Author leguang
 * @E-mail langmanleguang@qq.com
 * @Blog https://github.com/leguang
 * @Time 2016/4/1/0001 17:21
 * Description:
 */
public abstract class Controller {
    protected View mView;
    protected Context mContext;

    public Controller(Context context) {
        this.mContext = context;
        this.mView = initView();
        initData();
    }

    /**
     * 初始化View
     *
     * @return 返回根布局的View
     */
    public abstract View initView();

    /**
     * 初始化数据的方法，孩子如果有数据初始化，就复写
     */
    public void initData() {
    }

    public View getView() {
        return mView;
    }

    public Context getContext() {
        return mContext;
    }

    public void onDestroy() {
        mContext = null;
        mView = null;
    }
}
