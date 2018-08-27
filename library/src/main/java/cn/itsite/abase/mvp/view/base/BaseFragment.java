package cn.itsite.abase.mvp.view.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;

import cn.itsite.abase.exception.ExceptionHandler;
import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.adialog.dialog.LoadingDialog;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 * <p>
 * 所有Fragment的基类。将Fragment作为View层对象，专职处理View的试图渲染和事件。
 */
public abstract class BaseFragment<P extends BaseContract.Presenter> extends SwipeBackFragment implements BaseContract.View {
    private final String TAG = this.getClass().getSimpleName();
    public P mPresenter;
    private LoadingDialog loadingDialog;
    private ImmersionBar mImmersionBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
    }

    @NonNull
    protected P createPresenter() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.clear();
            mPresenter = null;
        }
        if (mImmersionBar != null) {
            try {
                mImmersionBar.destroy();
            } catch (Exception e) {
                ExceptionHandler.handle(e);
            }
        }
        hideSoftInput();
    }

    public P getPresenter() {
        return mPresenter;
    }

    public void setPresenter(@NonNull P presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    public void initStateBar(@NonNull View view) {
        ImmersionBar.setTitleBar(_mActivity, view);
    }

    /**
     * 用于被P层调用的通用函数。
     *
     * @param response
     */
    @CallSuper
    @Override
    public void start(Object response) {
        showLoading();
    }

    public void showLoading() {
        showLoading("玩命加载中…");
    }

    public void showLoading(String message) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(_mActivity);
            loadingDialog.setDimAmount(0);
        } else {
            loadingDialog.setText(message);
        }
        loadingDialog.show();
    }

    @Override
    public void error(Object error) {
        dismissLoading();
    }

    public void dismissLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    @CallSuper
    public void complete(Object response) {
        dismissLoading();
    }
}
