package cn.itsite.abase.mvvm.view.base;

import android.content.Context;

import androidx.annotation.NonNull;

import cn.itsite.abase.Controller;
import cn.itsite.abase.mvvm.contract.base.BaseContract;

/**
 * Created by leguang on 2017/6/22 0022.
 * Email：langmanleguang@qq.com
 */

public abstract class BaseController<VM extends BaseContract.ViewModel> extends Controller implements BaseContract.View {
    public final String TAG = this.getClass().getSimpleName();
    protected VM mViewModel;

    public BaseController(Context context) {
        super(context);
        mViewModel = createViewModel();
    }

    @NonNull
    protected VM createViewModel() {
        return null;
    }

    public VM getViewModel() {
        return mViewModel;
    }

    public void setViewModel(@NonNull VM viewModel) {
        this.mViewModel = viewModel;
    }

}
