package com.kadem.kadem.ExceptionHandling;

public class InvalidIdException extends Exception{
    public InvalidIdException(String errorMessage) {
        super(errorMessage);
    }
}
