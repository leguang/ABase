package cn.itsite.abase.mvp.view.base;

import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OSUtils;

import cn.itsite.abase.common.ActivityHelper;
import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.adialog.dialog.BaseDialog;
import cn.itsite.adialog.dialog.LoadingDialog;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;


/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public abstract class BaseActivity<P extends BaseContract.Presenter> extends SwipeBackActivity implements BaseContract.View {
    public static final String TAG = BaseActivity.class.getSimpleName();
    public static final String NAVIGATIONBAR_IS_MIN = "navigationbar_is_min";
    public P mPresenter;
    public ImmersionBar mImmersionBar;
    public BaseDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
        initStateBar();
        mPresenter = createPresenter();
    }

    private void initActivity() {
        //把每一个Activity加入栈中
        ActivityHelper.getInstance().addActivity(this);
    }

    protected void initStateBar() {
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2F);
        mImmersionBar.keyboardEnable(true)
                .navigationBarWithKitkatEnable(false)
                .init();
        //解决华为emui3.0与3.1手机手动隐藏底部导航栏时，导航栏背景色未被隐藏的问题
        if (OSUtils.isEMUI3_1()) {
            //第一种
            getContentResolver().registerContentObserver(Settings.System.getUriFor
                    (NAVIGATIONBAR_IS_MIN), true, new ContentObserver(new Handler()) {
                @Override
                public void onChange(boolean selfChange) {
                    int navigationBarIsMin = Settings.System.getInt(getContentResolver(),
                            NAVIGATIONBAR_IS_MIN, 0);
                    if (navigationBarIsMin == 1) {
                        //导航键隐藏了
                        mImmersionBar.transparentNavigationBar().init();
                    } else {
                        //导航键显示了
                        mImmersionBar.navigationBarColor(android.R.color.black) //隐藏前导航栏的颜色
                                .fullScreen(false)
                                .init();
                    }
                }
            });
            //第二种,禁止对导航栏的设置
            //mImmersionBar.navigationBarEnable(false).init();
        }
    }

    @NonNull
    protected P createPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.clear();
            mPresenter = null;
        }
        //把每一个Activity弹出栈。
        ActivityHelper.getInstance().removeActivity(this);
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
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
            loadingDialog = new LoadingDialog(this);
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
