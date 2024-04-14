package com.techstackgo.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserException extends Exception {
    public UserException(String message){
        super(message);
    }
}
