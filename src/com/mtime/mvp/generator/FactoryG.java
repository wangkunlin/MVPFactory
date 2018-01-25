package com.mtime.mvp.generator;

import com.mtime.mvp.exception.GeneratorException;
import com.mtime.mvp.log.L;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;

/**
 * Created by mtime
 * on 2018/1/24.
 */
class FactoryG extends AbsGenerator {

    FactoryG(String pkg, String path, String name) {
        super(path, pkg, name);
    }

    void generate() throws GeneratorException {
        String factoryName = name + "PresenterFactory";
        L.i("generating " + factoryName);
        String presenterName = name + "Presenter";

        ClassName contract_Presenter = ClassName.get(pkg, name + "Contract.Presenter");
        ClassName presenterFactory = ClassName.get("com.mtime.base.mvp", "PresenterFactory");
        ClassName presenter = ClassName.get(pkg, presenterName);

        TypeName presenterView = ParameterizedTypeName.get(presenterFactory, contract_Presenter);

        MethodSpec create = MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PUBLIC)
                .returns(contract_Presenter)
                .addAnnotation(Override.class)
                .addStatement("return new $T()", presenter)
                .build();

        TypeSpec factory = TypeSpec.classBuilder(factoryName)
                .addSuperinterface(presenterView)
                .addMethod(create)
                .build();

        writeFile(factoryName, factory);
    }
}
