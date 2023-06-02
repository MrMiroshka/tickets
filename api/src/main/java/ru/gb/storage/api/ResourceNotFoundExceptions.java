package ru.gb.storage.api;

public class ResourceNotFoundExceptions extends RuntimeException{
    public ResourceNotFoundExceptions(String message) {
        super(message);
    }
}
