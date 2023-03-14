package ru.practicum.exception;

public class AccessFailedException extends RuntimeException {
    public AccessFailedException(String message) {
        super(message);
    }
}
