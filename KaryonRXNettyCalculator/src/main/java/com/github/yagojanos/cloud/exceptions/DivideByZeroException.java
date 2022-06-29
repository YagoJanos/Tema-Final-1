package com.github.yagojanos.cloud.exceptions;

public class DivideByZeroException extends RuntimeException{

    public DivideByZeroException(){
        super("You can't divide a number by zero");
    }
}
