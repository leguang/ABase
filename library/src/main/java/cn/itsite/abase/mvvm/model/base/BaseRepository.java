package cn.itsite.abase.mvvm.model.base;

import cn.itsite.abase.mvvm.contract.base.BaseContract;

/**
 * Author：leguang on 2016/10/9 0009 10:35
 * Email：langmanleguang@qq.com
 * <p>
 * 所有Model类的基类，负责模型层的内容，包括数据获取和处理以及部分业务逻辑代码。
 */
public abstract class BaseRepository implements BaseContract.Model {
    public final String TAG = BaseRepository.class.getSimpleName();
}
