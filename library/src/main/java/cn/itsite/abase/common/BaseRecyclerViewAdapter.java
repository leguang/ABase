package cn.itsite.abase.common;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

/**
 * @version v0.0.0
 * @Author leguang
 * @E-mail langmanleguang@qq.com
 * @Blog https://github.com/leguang
 * @Time 2016/4/1/0001 17:21
 * Description: 此类作用仅为隔离BaseRecyclerViewAdapterHelper这个框架
 */
public abstract class BaseRecyclerViewAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public BaseRecyclerViewAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

    public BaseRecyclerViewAdapter(int layoutResId) {
        super(layoutResId);
    }
}
