package com.fssa.todo.exception;


public class UserRegistrationException extends  RuntimeException{
    public UserRegistrationException(String message){
        super(message);
    }
}
