package com.mtime.mvp.generator;

import com.mtime.mvp.exception.GeneratorException;
import com.mtime.mvp.log.L;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

/**
 * Created by mtime
 * on 2018/1/24.
 */
class ContractG extends AbsGenerator {

    ContractG(String pkg, String path, String name) {
        super(path, pkg, name);
    }

    void generate() throws GeneratorException {
        name += "Contract";
        L.i("generating " + name);
        TypeSpec viewType = TypeSpec.interfaceBuilder("View").addModifiers(Modifier.PUBLIC, Modifier.STATIC).build();

        ClassName basePresenter = ClassName.get("com.mtime.base.mvp", "BasePresenter");
        ClassName view = ClassName.get(pkg + "." + name, "View");
        TypeName presenterView = ParameterizedTypeName.get(basePresenter, view);

        TypeSpec presenterType = TypeSpec.interfaceBuilder("Presenter").addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addSuperinterface(presenterView)
                .build();

        TypeSpec contract = TypeSpec.interfaceBuilder(name)
                .addType(viewType)
                .addType(presenterType)
                .build();

        writeFile(name, contract);
    }
}
