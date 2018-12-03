package io.cutebot.doka2.jersey.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;


public abstract class AbstractWebExceptionMapper<T extends Exception> implements ExceptionMapper<T> {
    protected Response toResponse(T exception, int code) {
        return getResponseDto(code, exception.getMessage());
    }

    protected Response getResponseDto(int code, String message) {
        ErrorDto dto = new ErrorDto();
        dto.code = code;
        dto.codeDescription = Response.Status.fromStatusCode(code).getReasonPhrase();
        dto.message = message;
        return Response.status(code).entity(dto).build();
    }
}
