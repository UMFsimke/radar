package com.simicaleksandar.radar.generator;

import com.simicaleksandar.radar.model.inner.ViewHolderAnnotatedClass;
import com.simicaleksandar.radar.model.inner.ViewHolderGroupedClasses;
import com.squareup.javapoet.JavaFile;

import java.util.ArrayList;
import java.util.List;

public class ViewHolderGroupGenerator {

    private AdapterDelegateGenerator adapterDelegateGenerator;

    public ViewHolderGroupGenerator() {
        adapterDelegateGenerator = new AdapterDelegateGenerator();
    }

    public List<JavaFile> brewJava(ViewHolderGroupedClasses group) {
        if (group == null || group.getAnnotatedClassList() == null ||
                group.getAnnotatedClassList().size() == 0) return null;

        List<JavaFile> brewedJavaFiles = new ArrayList<>(group.getAnnotatedClassList().size());
        for (ViewHolderAnnotatedClass viewHolderAnnotatedClass : group.getAnnotatedClassList()) {
            JavaFile brewedJavaFile = adapterDelegateGenerator.brewJava(viewHolderAnnotatedClass);
            if (brewedJavaFile == null) continue;

            brewedJavaFiles.add(brewedJavaFile);
        }

        return brewedJavaFiles;
    }
}
