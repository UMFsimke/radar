package com.simicaleksandar.radar.model;

import com.simicaleksandar.radar.exceptions.QualifiedNameAlreadyUsedException;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.lang.model.element.TypeElement;

public class AdapterFactoryGroupedInterfaces {

    private String qualifiedClassName;
    private Map<String, AdapterFactoryAnnotatedInterface> itemsMap;

    public AdapterFactoryGroupedInterfaces(String qualifiedClassName) {
        this.qualifiedClassName = qualifiedClassName;
        itemsMap = new LinkedHashMap<String, AdapterFactoryAnnotatedInterface>();
    }

    public void add(AdapterFactoryAnnotatedInterface toInsert) throws QualifiedNameAlreadyUsedException {
        AdapterFactoryAnnotatedInterface existing = itemsMap.get(toInsert.getQualifiedName());
        if (existing != null) {
            throw new QualifiedNameAlreadyUsedException(existing);
        }

        itemsMap.put(toInsert.getQualifiedName(), toInsert);
    }

    public void ensureExists(TypeElement element) {
        AdapterFactoryAnnotatedInterface adapterFactoryAnnotatedInterface =
                new AdapterFactoryAnnotatedInterface(element);
        if (adapterFactoryAnnotatedInterface.)
    }
}
