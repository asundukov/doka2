package io.cutebot.telegram.tgmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TgChatShort {
    public Long id;

    public String type;

    public String title;

    @JsonProperty("user_name")
    public String userName;

    @JsonProperty("first_name")
    public String firstName;

    @JsonProperty("last_name")
    public String lastName;

    @JsonProperty("all_members_are_administrators")
    public Boolean allMembersAreAdministrators;

    @Override
    public String toString() {
        return "TgChatShort{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", allMembersAreAdministrators=" + allMembersAreAdministrators +
                '}';
    }
}
