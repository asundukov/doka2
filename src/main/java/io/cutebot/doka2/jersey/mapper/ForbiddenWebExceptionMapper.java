package io.cutebot.doka2.jersey.mapper;

import io.cutebot.doka2.jersey.webexception.ForbiddenWebException;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.FORBIDDEN;


public class ForbiddenWebExceptionMapper extends AbstractWebExceptionMapper<ForbiddenWebException> {
    @Override
    public Response toResponse(ForbiddenWebException exception) {
        return super.toResponse(exception, FORBIDDEN.getStatusCode());
    }
}
