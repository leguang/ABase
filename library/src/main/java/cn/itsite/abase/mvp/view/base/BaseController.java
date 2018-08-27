package cn.itsite.abase.mvp.view.base;

import android.content.Context;
import android.view.View;

/**
 * Created by leguang on 2017/6/22 0022.
 * Email：langmanleguang@qq.com
 */

public abstract class BaseController {
    public View mRootView;
    protected Context mContext;

    public BaseController(Context context) {
        this.mContext = context;
        this.mRootView = initView();
        initData();
    }

    /**
     * 初始化View
     *
     * @return
     */
    protected abstract View initView();

    /**
     * 初始化数据的方法，孩子如果有数据初始化，就复写
     */
    public void initData() {
    }
}
