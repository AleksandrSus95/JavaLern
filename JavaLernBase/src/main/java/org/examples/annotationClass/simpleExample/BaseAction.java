package org.examples.annotationClass.simpleExample;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseAction {
    int level();
    String sqlRequest();
}
