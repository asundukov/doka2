package io.cutebot.doka2.jersey.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class BadRequestErrorDto  extends ErrorDto {
    @JsonProperty("validation_errors")
    public List<ValidationErrorDto> validationErrors;

    BadRequestErrorDto() {
        code = 400;
        codeDescription = "Bad Request";
    }
}
