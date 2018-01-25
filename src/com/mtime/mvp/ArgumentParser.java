package com.mtime.mvp;

import com.mtime.mvp.exception.GeneratorException;
import com.mtime.mvp.generator.MVPGenerator;
import com.mtime.mvp.log.HelpLog;
import com.mtime.mvp.log.L;

import java.io.File;

/**
 * Created by mtime
 * on 2018/1/24.
 */
class ArgumentParser {

    private MVPGenerator.Builder builder = null;

    void parse(String[] args) {
        if (args == null || args.length == 0) {
            L.e("no args!!!");
            HelpLog.printHelp();
            return;
        }
        try {
            for (int i = 0; i < args.length; ++i) {
                switch (args[i]) {
                    case "-h":
                        HelpLog.printHelp();
                        return;
                    case "-b":
                        ensureBuilder();
                        String base = pickValue(args, ++i, "-b");
                        checkArg("-b", base);
                        builder.useBase(Boolean.parseBoolean(base));
                        break;
                    case "-p":
                        ensureBuilder();
                        String path = pickValue(args, ++i, "-p");
                        checkArg("-p", path);
                        if (path.startsWith("/") || path.startsWith(".") || path.startsWith("..")) {
                            L.e("path can not starts with '/' , '.' or '..'");
                            return;
                        }
                        String currentPath = System.getProperty("user.dir");
                        L.i("current dir is " + currentPath);
                        builder.setPath(new File(currentPath, path).getAbsolutePath());
                        break;
                    case "-f":
                        ensureBuilder();
                        String fn = pickValue(args, ++i, "-f");
                        checkArg("-f", fn);
                        if (builder.isActivitySet()) {
                            L.e("-f or -a can only use one of them");
                            return;
                        }
                        builder.setFragment(fn);
                        break;
                    case "-a":
                        ensureBuilder();
                        String an = pickValue(args, ++i, "-a");
                        checkArg("-a", an);
                        if (builder.isFragmentSet()) {
                            L.e("-f or -a can only use one of them");
                            return;
                        }
                        builder.setActivity(an);
                        break;
                    case "-pkg":
                        ensureBuilder();
                        String pkg = pickValue(args, ++i, "-pkg");
                        checkArg("-pkg", pkg);
                        builder.setPackage(pkg);
                        break;
                }
            }
            if (builder == null) {
                L.e("no valid args!!!");
                HelpLog.printHelp();
                return;
            }
            MVPGenerator generator = builder.build();
            generator.run();
        } catch (GeneratorException e) {
            L.e(e.getMessage());
        }
    }

    private String pickValue(String[] args, int index, String msg) throws GeneratorException {
        if (index >= args.length) {
            throw new GeneratorException("argument " + msg + " no value");
        }
        return args[index];
    }

    private void checkArg(String msg, String arg) throws GeneratorException {
        if (arg.startsWith("-")) {
            throw new GeneratorException("argument " + msg + " no value");
        }
    }

    private void ensureBuilder() {
        if (builder == null) {
            builder = new MVPGenerator.Builder();
        }
    }
}
