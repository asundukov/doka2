package io.cutebot.doka2.jersey.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidationErrorDto {
    @JsonProperty("field_name")
    public String fieldName;

    @JsonProperty("constraint_type")
    public String constraintType;
}
