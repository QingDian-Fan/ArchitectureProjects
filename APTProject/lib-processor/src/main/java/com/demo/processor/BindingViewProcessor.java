package com.demo.processor;

import com.demo.annotation.BindView;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;


@AutoService(Processor.class)
public class BindingViewProcessor extends AbstractProcessor {

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        for (Element element : roundEnvironment.getRootElements()) {
            //获取包名
            String packageString = element.getEnclosingElement().toString();
            //获取类名
            String classString = element.getSimpleName().toString();
            //构建新的类的名字：原类名 + BindingView
            ClassName className = ClassName.get(packageString, classString + "BindingView");
            //构建新的类的构造方法
            MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.get(packageString, classString), "activity");
            //判断是否要生成新的类，假如该类里面 BindView 注解，那么就不需要新生成
            boolean isBuild = false;

            //获取类的元素，例如类的成员变量、方法、内部类等
            for (Element enclosedElement : element.getEnclosedElements()) {
                //仅获取成员变量
                if (enclosedElement.getKind() == ElementKind.FIELD) {
                    BindView bindView = enclosedElement.getAnnotation(BindView.class);
                    if (bindView != null) {
                        //设置需要生成类
                        isBuild = true;
                        //在构造方法中加入代码
                        constructorBuilder.addStatement("activity.$N = activity.findViewById($L)", enclosedElement.getSimpleName(), bindView.value());
                    }
                }
            }
            //是否需要生成
            if (isBuild) {
                try {
                    //构建新的类
                    TypeSpec buildClass = TypeSpec.classBuilder(className)
                            .addModifiers(Modifier.PUBLIC)
                            .addMethod(constructorBuilder.build())
                            .build();
                    //生成 Java 文件
                    JavaFile.builder(packageString, buildClass).build().writeTo(filer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        //只支持 BindView 注解
        return Collections.singleton(BindView.class.getCanonicalName());
    }
}
