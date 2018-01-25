package com.mtime.mvp.generator;

import com.mtime.mvp.exception.GeneratorException;
import com.mtime.mvp.log.L;

/**
 * Created by mtime
 * on 2018/1/24.
 */
public class MVPGenerator {

    static final int FRAGMENT = 1;

    private boolean useBase;
    private String path;
    private String fragment;
    private String activity;
    private String pkg;
    private int who = 0;

    private MVPGenerator(Builder builder) throws GeneratorException {
        if (!builder.activitySet && !builder.fragmentSet) {
            throw new GeneratorException("not set fragment or activity");
        }
        if (builder.path == null) {
            throw new GeneratorException("mast have a path");
        }
        if (builder.activitySet) {
            activity = builder.activity;
        }
        if (builder.fragmentSet) {
            fragment = builder.fragment;
            who = FRAGMENT;
        }
        path = builder.path;
        useBase = builder.useBase;
        pkg = builder.aPackage;
    }

    public void run() throws GeneratorException {
        System.out.println();
        L.i("START GENERATE");

        String name = who == MVPGenerator.FRAGMENT ? fragment : activity;

        AbsGenerator contractG = new ContractG(pkg, path, name);
        contractG.generate();
        AbsGenerator presenterG = new PresenterG(pkg, path, name);
        presenterG.generate();
        AbsGenerator factoryG = new FactoryG(pkg, path, name);
        factoryG.generate();
        AbsGenerator viewG = new ViewG(pkg, path, name, who, useBase);
        viewG.generate();
        L.i("GENERATE ALL SUCCESSFUL");
        System.out.println();
    }

    public static class Builder {

        private boolean useBase;
        private String path;
        private boolean activitySet;
        private String fragment;
        private boolean fragmentSet;
        private String activity;
        private String aPackage;

        public void useBase(boolean use) {
            useBase = use;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public boolean isActivitySet() {
            return activitySet;
        }

        public void setFragment(String fragment) {
            this.fragment = fragment;
            fragmentSet = true;
        }

        public boolean isFragmentSet() {
            return fragmentSet;
        }

        public void setActivity(String activity) {
            this.activity = activity;
            activitySet = true;
        }

        public MVPGenerator build() throws GeneratorException {
            return new MVPGenerator(this);
        }

        public void setPackage(String aPackage) {
            this.aPackage = aPackage;
        }
    }
}
