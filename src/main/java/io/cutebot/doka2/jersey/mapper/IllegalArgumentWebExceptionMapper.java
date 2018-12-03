package io.cutebot.doka2.jersey.mapper;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;


public class IllegalArgumentWebExceptionMapper extends AbstractWebExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException e) {
        return super.toResponse(e, BAD_REQUEST.getStatusCode());
    }
}
