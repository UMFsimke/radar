package com.simicaleksandar.radar.generator;

import com.simicaleksandar.radar.model.inner.ViewHolderAnnotatedClass;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import radar.RadarViewHolder;

public final class AdapterDelegateGenerator implements Generator<ViewHolderAnnotatedClass> {

    private static final ClassName ACTIVITY = ClassName.get("android.app", "Activity");
    private static final ClassName OVERRIDE = ClassName.get("java.lang", "Override");

    @Override
    public JavaFile brewJava(ViewHolderAnnotatedClass viewHolderAnnotatedClass) {
        if (viewHolderAnnotatedClass == null) return null;

        return JavaFile.builder(viewHolderAnnotatedClass.getPackageName(),
                createType(viewHolderAnnotatedClass))
                .addFileComment("Generated code from Radar. Do not modify!")
                .build();
    }

    private TypeSpec createType(ViewHolderAnnotatedClass viewHolderAnnotatedClass) {
        final String adapterDelegateName = String.format("%s_RadarAdapterDelegate",
                viewHolderAnnotatedClass.getSimpleTypeName());
        final ParameterizedTypeName parameterizedTypeName =
                ParameterizedTypeName.get(ClassName.get("com.simicaleksandar.radar.inner", "RecyclerViewAdapterDelegate"),
                        TypeName.get(viewHolderAnnotatedClass.getDisplayedModel()));
        TypeSpec.Builder result = TypeSpec.classBuilder(adapterDelegateName)
                .superclass(parameterizedTypeName);

        result.addMethod(getAdapterDelegateConstructor(viewHolderAnnotatedClass));
        result.addMethod(getViewHolderDelegateMethod(viewHolderAnnotatedClass));

        return result.build();
    }

    private MethodSpec getAdapterDelegateConstructor(
            ViewHolderAnnotatedClass viewHolderAnnotatedClass) {
        CodeBlock.Builder codeBuilder = CodeBlock.builder()
                .add("super($N, $T.class, $L)", "activity",
                        TypeName.get(viewHolderAnnotatedClass.getDisplayedModel()),
                        viewHolderAnnotatedClass.getLayoutId().asCode());

        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ACTIVITY, "activity")
                .addStatement("$L", codeBuilder.build())
                .build();
    }

    private MethodSpec getViewHolderDelegateMethod(ViewHolderAnnotatedClass viewHolderAnnotatedClass) {

        ParameterizedTypeName parameterizedTypeName =
                parameterizeTypeViewHolder(viewHolderAnnotatedClass.getDisplayedModel());

        return MethodSpec.methodBuilder("getViewHolderDelegate")
                .addAnnotation(OVERRIDE)
                .addModifiers(Modifier.PROTECTED)
                .returns(parameterizedTypeName)
                .addStatement("return new $T()",
                        TypeName.get(viewHolderAnnotatedClass.getAnnotatedElement().asType()))
                .build();
    }

    private ParameterizedTypeName parameterizeTypeViewHolder(TypeMirror typeMirror) {
        return ParameterizedTypeName.get(ClassName.get(RadarViewHolder.class),
                TypeName.get(((DeclaredType) typeMirror).asElement().asType()));
    }
}
