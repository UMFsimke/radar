package com.simicaleksandar.radar.model.inner;

import com.simicaleksandar.radar.exceptions.QualifiedNameAlreadyUsedException;
import com.simicaleksandar.radar.model.inner.AdapterFactoryAnnotatedInterface;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdapterFactoryGroupedInterfaces {

    private String qualifiedClassName;
    private Map<String, AdapterFactoryAnnotatedInterface> itemsMap;

    public AdapterFactoryGroupedInterfaces(String qualifiedClassName) {
        this.qualifiedClassName = qualifiedClassName;
        itemsMap = new LinkedHashMap<>();
    }

    public void add(AdapterFactoryAnnotatedInterface toInsert) throws
            QualifiedNameAlreadyUsedException {
        AdapterFactoryAnnotatedInterface existing = itemsMap.get(toInsert.getQualifiedName());
        if (existing != null) {
            throw new QualifiedNameAlreadyUsedException(existing);
        }

        itemsMap.put(toInsert.getQualifiedName(), toInsert);
    }

    public boolean doesExists(AdapterFactoryAnnotatedInterface interfaceToCheck) {
        return itemsMap != null && itemsMap.containsKey(interfaceToCheck.getQualifiedName());
    }
}
