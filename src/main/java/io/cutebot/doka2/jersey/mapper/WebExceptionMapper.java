package io.cutebot.doka2.jersey.mapper;

import io.cutebot.doka2.jersey.webexception.WebException;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;


public class WebExceptionMapper extends AbstractWebExceptionMapper<WebException> {
        @Override
        public Response toResponse(WebException exception) {
            return toResponse(exception, INTERNAL_SERVER_ERROR.getStatusCode());
        }
}
