package io.cutebot.doka2.jersey.mapper;

import io.cutebot.doka2.jersey.webexception.BadRequestWebException;

import javax.ws.rs.core.Response;

public class BadRequestWebExceptionMapper extends AbstractWebExceptionMapper<BadRequestWebException> {
    @Override
    public Response toResponse(BadRequestWebException exception) {
        return super.toResponse(exception, 400);
    }
}
