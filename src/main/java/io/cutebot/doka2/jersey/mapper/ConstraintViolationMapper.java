package io.cutebot.doka2.jersey.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.ArrayList;

public class ConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {

        BadRequestErrorDto dto = new BadRequestErrorDto();
        dto.validationErrors = new ArrayList<>();

        e.getConstraintViolations().stream()
                .map(this::getValidationError)
                .forEach(validationErrorDto -> dto.validationErrors.add(validationErrorDto));

        dto.message = "There are some validation errors";

        return Response.status(Response.Status.BAD_REQUEST).entity(dto).build();
    }

    private ValidationErrorDto getValidationError(ConstraintViolation<?> violation) {
        ValidationErrorDto dto = new ValidationErrorDto();
        String fieldName = "";
        for (Path.Node node : violation.getPropertyPath()) {
            fieldName = node.getName();
        }
        Object o = violation.getExecutableParameters()[0];
        try {
            if ("arg0".equals(fieldName) || "dto".equals(fieldName)) {
                dto.fieldName = "root";
            } else {
                dto.fieldName = getJsonFieldName(dto, fieldName, o);
            }
            dto.constraintType = getConstraintName(violation);
        } catch (NoSuchFieldException ignored) {
        }
        return dto;
    }

    private String getConstraintName(ConstraintViolation<?> violation) {
        String fullName = violation.getConstraintDescriptor()
                .getAnnotation()
                .annotationType()
                .getCanonicalName();
        return fullName.substring(fullName.lastIndexOf('.') + 1);
    }

    private String getJsonFieldName(ValidationErrorDto dto, String fieldName, Object o) throws NoSuchFieldException {
        JsonProperty jsonProperty = o.getClass()
                .getField(fieldName)
                .getAnnotation(JsonProperty.class);

        if (jsonProperty == null) {
            return fieldName;
        } else {
            return jsonProperty.value();
        }
    }
}
