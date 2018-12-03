package io.cutebot.doka2.jersey.webexception;

public class BadRequestWebException extends WebException {
    public BadRequestWebException(Exception e) {
        super(e);
    }

    public BadRequestWebException(String message) {
        super(message);
    }
}
