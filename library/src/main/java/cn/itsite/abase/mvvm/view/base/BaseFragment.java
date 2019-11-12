package cn.itsite.abase.mvvm.view.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.gyf.barlibrary.ImmersionBar;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cn.itsite.abase.mvvm.contract.base.BaseContract;
import cn.itsite.abase.mvvm.viewmodel.base.BaseViewModel;
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
public abstract class BaseFragment<VM extends BaseViewModel> extends SwipeBackFragment implements BaseContract.View {
    public final String TAG = this.getClass().getSimpleName();
    protected VM mViewModel;
    protected ImmersionBar mImmersionBar;
    protected Dialog loadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
    }

    private void initViewModel() {
        mViewModel = onCreateViewModel();
        if (mViewModel == null) {
            createViewModel();
        }
        if (mViewModel != null) {
            getLifecycle().addObserver(mViewModel);
            mViewModel.loading.observe(this, o -> {
                onLoading(o);
            });
            mViewModel.complete.observe(this, o -> {
                onComplete(o);
            });
            mViewModel.error.observe(this, o -> {
                onError(o);
            });
        }
    }

    /**
     * 借助泛型来自动生成对应的ViewModel对象
     *
     * @return
     */
    private VM createViewModel() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            Class<VM> vmClass = (Class<VM>) types[0];
            mViewModel = ViewModelProviders.of(this).get(vmClass);
        }

        return mViewModel;
    }

    protected VM onCreateViewModel() {
        return onBindViewModel() != null ? ViewModelProviders.of(this).get(onBindViewModel()) : null;
    }

    protected Class<VM> onBindViewModel() {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(mViewModel);
    }

    public VM getViewModel() {
        return mViewModel;
    }

    public void setViewModel(@NonNull VM viewModel) {
        this.mViewModel = viewModel;
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
        if (mViewModel != null) {
            mViewModel.onInitialize();
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (mViewModel != null) {
            mViewModel.onVisible();
        }
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        if (mViewModel != null) {
            mViewModel.onInvisible();
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
            ;
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
            BaseFragment.this.onComplete("");
        }

        public abstract void onSuccess(T t);
    }

    public abstract class ResponseConsumer<T extends Response> implements Consumer<T> {

        @Override
        public void accept(T response) {
            if (response.isSuccessful()) {
                onSuccess(response);
            } else {
                BaseFragment.this.onError(response);
            }
        }

        public abstract void onSuccess(T t);
    }
}
