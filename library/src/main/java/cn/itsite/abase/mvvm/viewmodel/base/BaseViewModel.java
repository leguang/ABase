package cn.itsite.abase.mvvm.viewmodel.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import cn.itsite.abase.mvvm.contract.base.BaseContract;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.Response;

/**
 * Author：leguang on 2016/10/9 0009 10:31
 * Email：langmanleguang@qq.com
 * <p>
 * 所有ViewModel类的基类，负责调度View层和Model层的交互。
 */
public class BaseViewModel<M extends BaseContract.Model> extends AndroidViewModel implements BaseContract.ViewModel {
    public final String TAG = this.getClass().getSimpleName();
    protected M mModel;
    public MutableLiveData<Void> loading = new MutableLiveData<>();
    public MutableLiveData<Void> complete = new MutableLiveData<>();
    public MutableLiveData<Object> error = new MutableLiveData<>();
    /**
     * 管理Observables 和 Subscribers订阅
     */
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mModel = onCreateModel();
    }

    @NonNull
    protected M onCreateModel() {
        return null;
    }

    public void setModel(@NonNull M model) {
        this.mModel = model;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        //优先释放Model层对象，避免内存泄露
        if (mModel != null) {
            mModel.onClear();
            mModel = null;
        }

        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    protected void loading() {
        loading.postValue(null);
    }

    protected void complete() {
        complete.setValue(null);
    }

    protected void error(Object request) {
        error.postValue(request);
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
