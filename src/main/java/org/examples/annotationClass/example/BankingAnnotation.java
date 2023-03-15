package org.examples.annotationClass.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.security.Security;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BankingAnnotation {
    //При отсутствии явного значения securityLevel будет использовано дефолтное.
    SecurityLevelType securityLevel() default SecurityLevelType.MEDIUM;
}
