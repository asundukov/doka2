package io.cutebot.doka2.jersey.mapper;

import com.fasterxml.jackson.databind.JsonMappingException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class JsonMappingExceptionMapper implements ExceptionMapper<JsonMappingException> {
    @Override
    public Response toResponse(JsonMappingException e) {
        BadRequestErrorDto dto = new BadRequestErrorDto();
        dto.message = "Invalid object format";
        return Response.status(Response.Status.BAD_REQUEST).entity(dto).build();
    }
}
