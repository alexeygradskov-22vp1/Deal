package ru.gav.creditbank.deal.exception;

public class NotValidData extends RuntimeException {
    public NotValidData(String message) {
        super(message);
    }
}
