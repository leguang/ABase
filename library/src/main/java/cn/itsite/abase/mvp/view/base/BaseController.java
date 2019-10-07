package cn.itsite.abase.mvp.view.base;

import android.content.Context;

import androidx.annotation.NonNull;

import cn.itsite.abase.Controller;
import cn.itsite.abase.mvp.contract.base.BaseContract;

/**
 * Created by leguang on 2017/6/22 0022.
 * Email：langmanleguang@qq.com
 */

public abstract class BaseController<P extends BaseContract.Presenter> extends Controller implements BaseContract.View {
    public final String TAG = this.getClass().getSimpleName();
    protected P mPresenter;

    public BaseController(Context context) {
        super(context);
        mPresenter = createPresenter();
    }

    @NonNull
    protected P createPresenter() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onClear();
            mPresenter = null;
        }
    }

    public P getPresenter() {
        return mPresenter;
    }

    public void setPresenter(@NonNull P presenter) {
        this.mPresenter = presenter;
    }
}
