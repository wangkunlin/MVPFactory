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
class PresenterG extends AbsGenerator {


    PresenterG(String pkg, String path, String name) {
        super(path, pkg, name);
    }

    void generate() throws GeneratorException {
        String presenterName = name + "Presenter";
        L.i("generating " + presenterName);

        ClassName contract_View = ClassName.get(pkg, name + "Contract.View");
        ClassName contract_Presenter = ClassName.get(pkg, name + "Contract.Presenter");

        MethodSpec onViewAttached = MethodSpec.methodBuilder("onViewAttached")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(contract_View, "view")
                .addAnnotation(Override.class)
                .addCode("mView = view;\n")
                .build();

        MethodSpec onViewDetached = MethodSpec.methodBuilder("onViewDetached")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addAnnotation(Override.class)
                .build();

        MethodSpec onDestroyed = MethodSpec.methodBuilder("onDestroyed")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addCode("mView = null;\n")
                .addAnnotation(Override.class)
                .build();

        TypeSpec presenter = TypeSpec.classBuilder(presenterName)
                .addField(FieldSpec.builder(contract_View, "mView", Modifier.PRIVATE).build())
                .addMethod(onViewAttached)
                .addMethod(onViewDetached)
                .addMethod(onDestroyed)
                .addSuperinterface(contract_Presenter)
                .build();

        writeFile(presenterName, presenter);
    }
}
