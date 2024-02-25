package org.examples.annotationClass.example;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class SecurityService {
    public void onInvoke(SecurityLevelType level, Method method, Object[] args){
        System.out.printf("%s() was invoked with parameters: %s ", method.getName(), Arrays.toString(args));
        switch (level){
            case LOW -> System.out.println("security: " + level);
            case MEDIUM -> System.out.println("security: " + level);
            case HIGH -> System.out.println("security: " + level);
            default -> throw new EnumConstantNotPresentException(SecurityLevelType.class, level.toString());
        }
    }
}
