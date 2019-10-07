package cn.itsite.abase.demo;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import cn.itsite.abase.mvvm.viewmodel.base.BaseViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel<MainModel> {
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onInitialize(Object... request) {
        Observable.create(emitter -> {
            Thread.sleep(4000);
            emitter.onComplete();
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<Object>() {
                    @Override
                    public void onSuccess(Object response) {
                        Log.e(TAG, "開始: " + response);
                    }
                });
    }
}
