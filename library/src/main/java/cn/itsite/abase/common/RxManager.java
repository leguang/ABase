package cn.itsite.abase.common;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Author：Administrator on 2016/10/9 0009 10:35
 * Email：langmanleguang@qq.com
 */
public class RxManager {
    private final String TAG = this.getClass().getSimpleName();

    /**
     * 管理Observables 和 Subscribers订阅
     */
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    /**
     * 单纯的Observables 和 Subscribers管理
     *
     * @param disposable
     */
    public void add(Disposable disposable) {
        /*订阅管理*/
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 单个presenter生命周期结束，取消订阅
     */
    public void clear() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}

