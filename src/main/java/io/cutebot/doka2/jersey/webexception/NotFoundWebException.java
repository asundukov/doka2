package io.cutebot.doka2.jersey.webexception;

public class NotFoundWebException extends WebException {
    public NotFoundWebException(Exception e) {
        super(e.getMessage(), e);
    }
}
