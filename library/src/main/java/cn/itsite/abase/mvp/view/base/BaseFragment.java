package cn.itsite.abase.mvp.view.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gyf.barlibrary.ImmersionBar;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.adialog.dialog.BaseDialog;
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
    public final String TAG = this.getClass().getSimpleName();
    public P mPresenter;
    public ImmersionBar mImmersionBar;
    public BaseDialog loadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
    }

    @NonNull
    protected P createPresenter() {
        return null;
    }

//    @Override
//    public void onSupportVisible() {
//        super.onSupportVisible();
//        if (mImmersionBar == null) {
//            mImmersionBar = ImmersionBar.with(this);
//            mImmersionBar.navigationBarWithKitkatEnable(false).init();
//        }
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        hideSoftInput();
    }

    /**
     * mPresenter在这里销毁是因为创建是在onCreate里创建的，最好对称。
     * 如果是在onCreateView里创建的，那就在onDestroyView里销毁。
     * 因为ViewPager缓存，当返回到曾经缓存的fragment时，presenter会为空，
     * 因为在onDestroyView里被置空了，但又不走onCreate，所以考虑是否把置空操作移动到onDestroy。
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.clear();
            mPresenter = null;
        }
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
    public void start(Object... response) {
        showLoading();
    }

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(_mActivity);
            loadingDialog.setDimAmount(0);
        }
        loadingDialog.show();
    }

    @Override
    public void error(Object... error) {
        dismissLoading();
    }

    public void dismissLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    @CallSuper
    public void complete(Object... response) {
        dismissLoading();
    }
}
