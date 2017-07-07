package com.simicaleksandar.radar.model;

import com.simicaleksandar.radar.model.ViewHolderAnnotatedClass;

import java.util.ArrayList;
import java.util.List;

public class ViewHolderGroupedClasses {

    private String qualifiedClassName;
    private List<ViewHolderAnnotatedClass> annotatedClassList;

    public ViewHolderGroupedClasses(String qualifiedClassName) {
        this.qualifiedClassName = qualifiedClassName;
    }

    public void add(ViewHolderAnnotatedClass annotatedClass) {
        //TODO: should we check if it already exists?! What would be unique ID in that case for Map
        if (annotatedClassList == null) {
            annotatedClassList = new ArrayList<>();
        }

        annotatedClassList.add(annotatedClass);
    }
}
