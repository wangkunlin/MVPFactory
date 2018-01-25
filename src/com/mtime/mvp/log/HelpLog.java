package com.mtime.mvp.log;

/**
 * Created by mtime
 * on 2018/1/24.
 */
public class HelpLog {

    public static void printHelp() {
        L.i("MVP 生成器 author 王坤林");
        L.i("  支持的参数:");
        L.i("   -a   Activity 的名字,不包含 Activity");
        L.i("   -f   Fragment 的名字,不包含 Fragment, -a 和 -f 不能同时使用");
        L.i("   -p   输出到目录（不包含包名），不能以 '/' , '.' , '..' 开头");
        L.i("   -b   指定是否使用 base 库中的 mvp, true or false, 默认 false");
        L.i("   -pkg 指定包名");
        L.i("   -h   输出帮助信息");
    }
}
