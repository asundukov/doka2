package io.cutebot.doka2.jersey.mapper;

import io.cutebot.doka2.jersey.webexception.NotFoundWebException;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;


public class NotFoundWebExceptionMapper extends AbstractWebExceptionMapper<NotFoundWebException> {
    @Override
    public Response toResponse(NotFoundWebException exception) {
        return super.toResponse(exception, NOT_FOUND.getStatusCode());
    }
}
