package org.examples.staticExample;

public class SomeCLass {
    private static int staticField;

    public SomeCLass(){
        staticField++;
        System.out.println(SomeCLass.class.getName() + " " + staticField);
    }
}
