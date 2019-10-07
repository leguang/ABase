package cn.itsite.abase;

import android.content.Context;
import android.view.View;

/**
 * Created by leguang on 2017/6/22 0022.
 * Email：langmanleguang@qq.com
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
