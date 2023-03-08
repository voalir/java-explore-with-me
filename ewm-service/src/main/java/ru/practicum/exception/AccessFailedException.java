package ru.practicum.exception;

public class AccessFailedException extends RuntimeException {
    public AccessFailedException(String s) {
        super(s);
    }
}
