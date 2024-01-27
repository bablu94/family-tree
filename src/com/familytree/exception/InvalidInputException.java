package com.familytree.exception;

public class InvalidInputException extends Exception {
    static final long serialVersionUID = -3387516993334229948L;

    public InvalidInputException(String message) {
        super(message);
    }
}
