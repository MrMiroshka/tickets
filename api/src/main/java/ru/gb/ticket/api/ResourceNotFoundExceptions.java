package ru.gb.ticket.api;

public class ResourceNotFoundExceptions extends RuntimeException{
    public ResourceNotFoundExceptions(String message) {
        super(message);
    }
}
