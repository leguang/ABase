package cn.itsite.abase.exception;

import com.orhanobut.logger.Logger;

/**
 * @author: leguang
 * @e-mail: langmanleguang@qq.com
 * @version: v0.0.0
 * @blog: https://github.com/leguang
 * @time: 2018/7/4 0004 11:48
 * @description:
 */
public class ExceptionHandler {
    public static void handle(Throwable t) {
        if (t != null) {
            t.printStackTrace();
            Logger.e(t.getMessage() + "");
        }
    }
}