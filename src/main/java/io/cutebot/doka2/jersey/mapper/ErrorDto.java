package io.cutebot.doka2.jersey.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class ErrorDto {
    public int code;
    @JsonProperty("code_description")
    public String codeDescription;
    public String message;
}
