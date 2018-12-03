package io.cutebot.doka2.jersey.webexception;

public class ForbiddenWebException extends WebException {
    public ForbiddenWebException() {
        this("Access denied");
    }

    public ForbiddenWebException(String message) {
        super(message);
    }
}
