package io.cutebot.doka2.jersey.mapper;

import com.fasterxml.jackson.core.JsonParseException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class JsonParseExceptionMapper implements ExceptionMapper<JsonParseException>  {
    @Override
    public Response toResponse(JsonParseException e) {
        BadRequestErrorDto dto = new BadRequestErrorDto();
        dto.message = e.getMessage();
        return Response.status(Response.Status.BAD_REQUEST).entity(dto).build();
    }
}
