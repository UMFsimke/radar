package com.simicaleksandar.radar.generator;

import com.simicaleksandar.radar.model.inner.ViewHolderAnnotatedClass;
import com.simicaleksandar.radar.model.inner.ViewHolderGroupedClasses;

public class ViewHolderGroupGenerator implements Generator<ViewHolderGroupedClasses> {

    AdapterDelegateGenerator adapterDelegateGenerator;

    @Override
    public void generate(ViewHolderGroupedClasses group) {
        if (group == null || group.getAnnotatedClassList() == null ||
                group.getAnnotatedClassList().size() == 0) return;

        for (ViewHolderAnnotatedClass viewHolderAnnotatedClass : group.getAnnotatedClassList()) {
            adapterDelegateGenerator.generate(viewHolderAnnotatedClass);
        }
    }
}
