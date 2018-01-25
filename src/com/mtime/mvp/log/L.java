package com.mtime.mvp.log;


import com.sun.media.jfxmedia.logging.Logger;

/**
 * Created by mtime
 * on 2018/1/24.
 */
public class L {

    static {
        Logger.setLevel(Logger.DEBUG);
    }

    public static void e(String s) {
        Logger.logMsg(Logger.ERROR, s);
    }

    public static void i(String s) {
        Logger.logMsg(Logger.INFO, s);
    }

    public static void w(String s) {
        Logger.logMsg(Logger.WARNING, s);
    }

    public static void d(String s) {
        Logger.logMsg(Logger.DEBUG, s);
    }

}
