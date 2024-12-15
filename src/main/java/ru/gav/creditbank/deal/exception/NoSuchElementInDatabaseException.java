package ru.gav.creditbank.deal.exception;

public class NoSuchElementInDatabaseException extends RuntimeException {
    public NoSuchElementInDatabaseException(String message) {
        super(message);
    }
}
