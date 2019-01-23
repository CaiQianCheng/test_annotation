package com.example.processor;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import com.example.my_annotation.*;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;


@AutoService(Processor.class)
public class MyProcessor extends AbstractProcessor {

    /**
     * 文件相关的辅助类
     */
    private Filer mFiler;


    //类构建器集合
    private Map<String, TypeSpec.Builder> classBuilderMap;

    //字段元素集合
    private Map<String, List<VariableElement>> fieldsMap;

    //方法元素集合
    private Map<String, List<ExecutableElement>> methodsMap;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        classBuilderMap = new HashMap<>();
        fieldsMap = new HashMap<>();
        methodsMap = new HashMap<>();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        List<Element> elements = new ArrayList<>();
        for (Element element : roundEnv.getElementsAnnotatedWith(ViewSeeker1.class)) {
            elements.add(element);
        }
        for (Element element : roundEnv.getElementsAnnotatedWith(ClickSeeker.class)) {
            elements.add(element);
        }
        //空的话不生成文件
        if (!elements.isEmpty()) {
            for (int i = 0; i < elements.size(); i++) {
                Element  element = elements.get(i);
                /*获取类信息*/
                TypeElement typeElement = (TypeElement) element.getEnclosingElement();
                /*类的绝对路径*/
                String qualifiedName = typeElement.getQualifiedName().toString();
                /*类名*/
                String clsName = typeElement.getSimpleName().toString();
                if (classBuilderMap.get(qualifiedName) == null) {
                    TypeSpec.Builder classBuilder = TypeSpec.classBuilder("Bind_" + clsName).addModifiers(Modifier.PUBLIC);
                    classBuilderMap.put(qualifiedName, classBuilder);
                }
                //注解的元素是变量
                if(elements.get(i) instanceof VariableElement){
                    VariableElement variableElement = (VariableElement)elements.get(i);
                    if (fieldsMap.get(qualifiedName) == null) {
                        List<VariableElement> fieldList = new ArrayList<>();
                        fieldsMap.put(qualifiedName, fieldList);
                    }
                    fieldsMap.get(qualifiedName).add(variableElement);
                }else{  //注解的元素是方法
                    ExecutableElement methodElement = (ExecutableElement)elements.get(i);
                    if (methodsMap.get(qualifiedName) == null) {
                        List<ExecutableElement> methodList = new ArrayList<>();
                        methodsMap.put(qualifiedName, methodList);
                    }
                    methodsMap.get(qualifiedName).add(methodElement);
                }
            }
            //判断 有几个类
            for (String key : classBuilderMap.keySet()) {
                TypeSpec.Builder classbuilder = classBuilderMap.get(key);
                String packName = key.substring(0, key.lastIndexOf("."));
                String clsName = key.substring(key.lastIndexOf(".") + 1);
                MethodSpec.Builder methodBuild = MethodSpec.methodBuilder("bindView")
                        .addParameter(ClassName.get(packName, clsName), "activity",Modifier.FINAL)
                        .addModifiers(Modifier.PUBLIC);
                StringBuilder code = new StringBuilder();
                //添加方法内容
                for (VariableElement variableElement : fieldsMap.get(key)) {
                    code.append("activity." + variableElement.getSimpleName().toString() + "=activity.findViewById(" + variableElement.getAnnotation(ViewSeeker1.class).value() + ");\n");
                }

                if(methodsMap.get(key)!=null){
                    for (ExecutableElement executableElement : methodsMap.get(key)) {
                        VariableElement variableElement=null;
                        for(VariableElement temp : fieldsMap.get(key)){
                            if(temp.getAnnotation(ViewSeeker1.class).value()
                                    ==executableElement.getAnnotation(ClickSeeker.class).value()){
                                variableElement = temp;
                                break;
                            }
                        }
                        if(variableElement==null){
                            continue;
                        }
                        code.append("activity." +  variableElement.getSimpleName().toString()
                                + ".setOnClickListener(view->{\n"
                                +"  activity." + executableElement.getSimpleName().toString() + "();\n});\n");
                    }
                }

                MethodSpec methodSpec = methodBuild.addCode(code.toString()).build();
                classbuilder.addMethod(methodSpec);
                //创建Java文件
                JavaFile javaFile = JavaFile.builder("com.example.administrator.test_annotation", classbuilder.build()).build();
                try {
                    javaFile.writeTo(mFiler);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotataions = new LinkedHashSet<>();
        annotataions.add(ViewSeeker1.class.getCanonicalName());
        annotataions.add(ClickSeeker.class.getCanonicalName());
        return annotataions;
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
