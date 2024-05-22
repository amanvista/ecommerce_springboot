package com.techstackgo.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class UserException extends Exception {
    public UserException(String message){
        super(message);
    }
}
