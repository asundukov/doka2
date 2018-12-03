package io.cutebot.telegram.tgmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TgUser {

    public Long id;
    @JsonProperty("is_bot")
    public Boolean isBot;

    @JsonProperty("first_name")
    public String firstName;

    @JsonProperty("last_name")
    public String lastName;

    @JsonProperty("username")
    public String userName;

    @JsonProperty("language_code")
    public String languageCode;

    @Override
    public String toString() {
        return "TgUser{" +
                "id=" + id +
                ", isBot=" + isBot +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", languageCode='" + languageCode + '\'' +
                '}';
    }
}
