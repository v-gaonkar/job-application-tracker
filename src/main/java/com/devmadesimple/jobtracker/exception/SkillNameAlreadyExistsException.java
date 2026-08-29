package com.devmadesimple.jobtracker.exception;

public class SkillNameAlreadyExistsException extends RuntimeException{
    public SkillNameAlreadyExistsException(String message) {
        super(message);
    }
}
