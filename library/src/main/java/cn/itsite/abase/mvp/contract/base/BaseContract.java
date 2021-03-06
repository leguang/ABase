package cn.itsite.abase.mvp.contract.base;

/**
 * Author：leguang on 2016/10/10 0010 20:44
 * Email：langmanleguang@qq.com
 * <p>
 * 所有契约接口的基类接口，定义了各层对象的生命周期。
 */
public interface BaseContract {
    interface View {
        /**
         * 用于加载loading操作
         *
         * @param response
         */
        default void onLoading(Object... response) {

        }

        /**
         * P层如果请求发生错误会调用该方法
         *
         * @param error
         */
        default void onError(Object... error) {

        }

        /**
         * P层请求完成后会调用该方法
         *
         * @param response
         */
        default void onComplete(Object... response) {

        }
    }

    interface Presenter {

        /**
         * Presenter的生命周期开始。
         */
        default void onInitialize(Object... request) {

        }

        /**
         * View可见时会调用该方法。
         */
        default void onVisible(Object... request) {

        }

        /**
         * View不可见时会调用该方法。
         */
        default void onInvisible(Object... request) {

        }

        /**
         * Presenter的生命周期结束，释放资源。
         */
        default void onClear() {

        }
    }

    interface Model {

        /**
         * Model的生命周期开始。
         */
        default void onInitialize(Object... request) {

        }

        /**
         * Model的生命周期结束，释放资源。
         */
        default void onClear() {

        }
    }
}
