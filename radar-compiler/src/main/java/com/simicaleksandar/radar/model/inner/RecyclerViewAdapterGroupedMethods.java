package com.simicaleksandar.radar.model.inner;

import com.simicaleksandar.radar.exceptions.MethodNameAlreadyUsedException;
import com.simicaleksandar.radar.exceptions.QualifiedNameAlreadyUsedException;

import java.util.LinkedHashMap;
import java.util.Map;

public class RecyclerViewAdapterGroupedMethods {

    private String qualifiedClassName;
    private Map<String, RecyclerViewAdapterAnnotatedMethod> itemsMap;

    public RecyclerViewAdapterGroupedMethods(String qualifiedClassName) {
        this.qualifiedClassName = qualifiedClassName;
        itemsMap = new LinkedHashMap<>();
    }

    public void add(RecyclerViewAdapterAnnotatedMethod toInsert) throws
            MethodNameAlreadyUsedException {
        RecyclerViewAdapterAnnotatedMethod existing = itemsMap.get(toInsert.getMethodName());
        if (existing != null) {
            throw new MethodNameAlreadyUsedException(existing);
        }

        itemsMap.put(toInsert.getMethodName(), toInsert);
    }
}
