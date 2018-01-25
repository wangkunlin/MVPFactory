package com.mtime.mvp.generator;

import com.mtime.mvp.exception.GeneratorException;
import com.mtime.mvp.log.L;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;

/**
 * Created by mtime
 * on 2018/1/25.
 */
abstract class AbsGenerator {

    protected String path;
    protected String pkg;
    protected String name;

    AbsGenerator(String path, String pkg, String name) {
        this.path = path;
        this.pkg = pkg;
        this.name = name;
    }

    abstract void generate() throws GeneratorException;

    void writeFile(String specName, TypeSpec spec) throws GeneratorException {
        JavaFile file = JavaFile.builder(pkg, spec).skipJavaLangImports(true).indent("    ").build();

        try {
            File javaFile = new File(path);
            file.writeTo(javaFile);
        } catch (IOException e) {
            throw new GeneratorException("generate " + specName + " Failed!!!");
        }
        L.i("generate " + specName + " done");
    }
}
