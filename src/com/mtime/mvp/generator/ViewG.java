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
class ViewG extends AbsGenerator {

    private int who;
    private boolean useBase;

    ViewG(String pkg, String path, String name, int who, boolean useBase) {
        super(path, pkg, name);
        this.who = who;
        this.useBase = useBase;
    }

    void generate() throws GeneratorException {
        String viewName = name + (who == MVPGenerator.FRAGMENT ? "Fragment" : "Activity");
        L.i("generating " + viewName);

        ClassName contract_View = ClassName.get(pkg, name + "Contract.View");
        ClassName contract_Presenter = ClassName.get(pkg, name + "Contract.Presenter");
        ClassName presenterFactory = ClassName.get(pkg, name + "PresenterFactory");
        ClassName basePresenterFactory = ClassName.get("com.mtime.base.mvp", "PresenterFactory");
        TypeName presenterView = ParameterizedTypeName.get(basePresenterFactory, contract_Presenter);

        ClassName nonNull = ClassName.get("android.support.annotation", "NonNull");

        MethodSpec getPresenterFactory = MethodSpec.methodBuilder("getPresenterFactory")
                .addStatement("return new $T()", presenterFactory)
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .addAnnotation(nonNull)
                .returns(presenterView)
                .build();

        MethodSpec onPresenterPrepared = MethodSpec.methodBuilder("onPresenterPrepared")
                .addModifiers(Modifier.PROTECTED)
                .addCode("mPresenter = presenter;\n")
                .returns(void.class)
                .addParameter(ParameterSpec.builder(contract_Presenter, "presenter").addAnnotation(nonNull).build())
                .addAnnotation(Override.class)
                .build();

        MethodSpec tag = MethodSpec.methodBuilder("tag")
                .addModifiers(Modifier.PROTECTED)
                .returns(String.class)
                .addCode("return TAG;\n")
                .addAnnotation(nonNull)
                .addAnnotation(Override.class)
                .build();

        TypeSpec view;
        if (who == MVPGenerator.FRAGMENT) {

            ClassName superClass;

            if (useBase) {
                superClass = ClassName.get("com.mtime.base.mvp", "MBasePresenterFragment");
            } else {
                superClass = ClassName.get("com.mtime.lookface.base", "BasePresenterFragment");
            }
            ParameterizedTypeName superType = ParameterizedTypeName.get(superClass, contract_Presenter, contract_View);

            view = TypeSpec.classBuilder(viewName)
                    .superclass(superType)
                    .addSuperinterface(contract_View)
                    .addField(contract_Presenter, "mPresenter", Modifier.PRIVATE)
                    .addField(FieldSpec.builder(String.class, "TAG", Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                            .initializer("$S", viewName)
                            .build())
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(getPresenterFactory)
                    .addMethod(onPresenterPrepared)
                    .addMethod(tag)
                    .build();
        } else {

            ClassName superClass;

            if (useBase) {
                superClass = ClassName.get("com.mtime.base.mvp", "MBasePresenterActivity");
            } else {
                superClass = ClassName.get("com.mtime.lookface.base", "BasePresenterActivity");
            }
            ParameterizedTypeName superType = ParameterizedTypeName.get(superClass, contract_Presenter, contract_View);

            view = TypeSpec.classBuilder(viewName)
                    .superclass(superType)
                    .addSuperinterface(contract_View)
                    .addField(contract_Presenter, "mPresenter", Modifier.PRIVATE)
                    .addField(FieldSpec.builder(String.class, "TAG", Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                            .initializer("$S", viewName)
                            .build())
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(getPresenterFactory)
                    .addMethod(onPresenterPrepared)
                    .addMethod(tag)
                    .build();
        }

        writeFile(viewName, view);
    }
}
