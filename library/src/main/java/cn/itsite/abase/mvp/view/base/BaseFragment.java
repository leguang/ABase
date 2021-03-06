package cn.itsite.abase.mvp.view.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gyf.barlibrary.ImmersionBar;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.adialog.dialog.LoadingDialog;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;
import retrofit2.Response;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 * <p>
 * 所有Fragment的基类。将Fragment作为View层对象，专职处理View的试图渲染和事件。
 */
public abstract class BaseFragment<P extends BaseContract.Presenter> extends SwipeBackFragment implements BaseContract.View {
    public final String TAG = this.getClass().getSimpleName();
    protected P mPresenter;
    protected ImmersionBar mImmersionBar;
    protected Dialog loadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = onCreatePresenter();
    }

    @NonNull
    protected P onCreatePresenter() {
        return null;
    }

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
    @Override
    public void onLoading(Object... response) {
        showLoading();
    }

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(_mActivity)
                    .setDimAmount(0);
        }
        loadingDialog.show();
    }

    @Override
    public void onError(Object... error) {
        dismissLoading();
    }

    public void dismissLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    @CallSuper
    public void onComplete(Object... response) {
        dismissLoading();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.onInitialize();
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (mPresenter != null) {
            mPresenter.onVisible();
        }
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        if (mPresenter != null) {
            mPresenter.onInvisible();
        }
    }

    public abstract class BaseObserver<T> implements Observer<T> {

        @Override
        public void onSubscribe(Disposable disposable) {
            onLoading();
        }

        @Override
        public void onNext(T response) {
            onSuccess(response);
        }

        @Override
        public void onError(Throwable throwable) {
            BaseFragment.this.onError(throwable);
        }

        @Override
        public void onComplete() {
            BaseFragment.this.onComplete();
        }

        public abstract void onSuccess(T response);
    }

    public abstract class ResponseObserver<T extends Response> implements Observer<T> {

        @Override
        public void onSubscribe(Disposable disposable) {
            onLoading();
        }

        @Override
        public void onNext(T response) {
            if (response.isSuccessful()) {
                onSuccess(response);
            } else {
                BaseFragment.this.onError(response);
            }
        }

        @Override
        public void onError(Throwable throwable) {
            BaseFragment.this.onError(throwable);
        }

        @Override
        public void onComplete() {
            BaseFragment.this.onComplete();
        }

        public abstract void onSuccess(T t);
    }

    public abstract class ResponseConsumer<T extends Response> implements Consumer<T> {

        @Override
        public void accept(T response) {
            if (response.isSuccessful()) {
                onSuccess(response);
            } else {
                onError(response);
            }
        }

        public abstract void onSuccess(T t);
    }
}
