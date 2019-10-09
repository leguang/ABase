package cn.itsite.abase.mvp.presenter.base;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.Response;


/**
 * Author：leguang on 2016/10/9 0009 10:31
 * Email：langmanleguang@qq.com
 * <p>
 * 所有Presenter类的基类，负责调度View层和Model层的交互。
 */
public class BasePresenter<V extends BaseContract.View, M extends BaseContract.Model> implements BaseContract.Presenter {
    public final String TAG = BasePresenter.class.getSimpleName();
    protected final Class<? extends BaseContract.View> mViewClass;
    protected Reference<V> mViewReference;
    protected M mModel;
    /**
     * 每一套mvp应该拥有一个独立的RxManager
     * 管理Observables 和 Subscribers订阅
     */
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public BasePresenter(V mView) {
        mViewClass = mView.getClass();
        setView(mView);
        mModel = onCreateModel();
    }

    @NonNull
    protected M onCreateModel() {
        return null;
    }

    public void setModel(@NonNull M model) {
        this.mModel = model;
    }

    public M getModel() {
        return mModel;
    }

    @UiThread
    public boolean isViewAttached() {
        return mViewReference != null && mViewReference.get() != null;
    }

    public V getView() {
        if (mViewReference != null && mViewReference.get() != null) {
            return mViewReference.get();
        }

        return (V) Proxy.newProxyInstance(mViewClass.getClassLoader(),
                mViewClass.getInterfaces(),
                (proxy, method, args) -> {
                    try {
                        Type type = method.getReturnType();
                        if (type == boolean.class) {
                            return false;
                        } else if (type == int.class) {
                            return 0;
                        } else if (type == short.class) {
                            return (short) 0;
                        } else if (type == char.class) {
                            return (char) 0;
                        } else if (type == byte.class) {
                            return (byte) 0;
                        } else if (type == long.class) {
                            return 0L;
                        } else if (type == float.class) {
                            return 0F;
                        } else if (type == double.class) {
                            return 0D;
                        } else {
                            return null;
                        }
                    } catch (Exception e) {
                        throw e.getCause();
                    }
                });
    }

    public void setView(V view) {
        mViewReference = new WeakReference<V>(view);
    }

    @Override
    public void onClear() {
        //优先释放Model层对象，避免内存泄露
        if (mModel != null) {
            mModel.onClear();
            mModel = null;
        }
        //释放View层对象，避免内存泄露
        if (mViewReference != null) {
            mViewReference.clear();
            mViewReference = null;
        }

        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    protected void loading() {
        if (isViewAttached()) {
            getView().onLoading();
        }
    }

    protected void complete() {
        if (isViewAttached()) {
            getView().onComplete();
        }
    }

    protected void error(Object request) {
        if (isViewAttached()) {
            getView().onComplete(request);
        }
    }

    /**
     * 单纯的Observables 和 Subscribers管理
     *
     * @param disposable
     */
    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public abstract class BaseObserver<T> implements Observer<T> {

        @Override
        public void onSubscribe(Disposable disposable) {
            addDisposable(disposable);
            loading();
        }

        @Override
        public void onNext(T response) {
            onSuccess(response);
        }

        @Override
        public void onError(Throwable throwable) {
            error(throwable);
        }

        @Override
        public void onComplete() {
            complete();
        }

        public abstract void onSuccess(T response);
    }

    public abstract class ResponseObserver<T extends Response> implements Observer<T> {

        @Override
        public void onSubscribe(Disposable disposable) {
            addDisposable(disposable);
            loading();
        }

        @Override
        public void onNext(T response) {
            if (response.isSuccessful()) {
                onSuccess(response);
            } else {
                error(response);
            }
        }

        @Override
        public void onError(Throwable throwable) {
            error(throwable);
        }

        @Override
        public void onComplete() {
            complete();
        }

        public abstract void onSuccess(T t);
    }

    public abstract class ResponseConsumer<T extends Response> implements Consumer<T> {

        @Override
        public void accept(T response) {
            if (response.isSuccessful()) {
                onSuccess(response);
            } else {
                error(response);
            }
        }

        public abstract void onSuccess(T t);
    }
}
