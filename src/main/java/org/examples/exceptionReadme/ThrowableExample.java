package org.examples.exceptionReadme;

public class ThrowableExample extends Exception{
    public ThrowableExample(){

    }

    public ThrowableExample(String message, Throwable cause){
        super(message, cause);
    }

    public ThrowableExample(String message){
        super(message);
    }

    public ThrowableExample(Throwable cause){
        super(cause);
    }
}
