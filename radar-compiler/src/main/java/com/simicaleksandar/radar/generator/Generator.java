package com.simicaleksandar.radar.generator;

import com.squareup.javapoet.JavaFile;

public interface Generator<T> {
    JavaFile brewJava(T element);
}
