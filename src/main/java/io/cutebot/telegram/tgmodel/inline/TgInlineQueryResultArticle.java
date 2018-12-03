package io.cutebot.telegram.tgmodel.inline;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class TgInlineQueryResultArticle extends TgInlineQueryResult {
    public final String type = "article";
    public String id;
    public String title;
    public String description;

    @JsonProperty("input_message_content")
    public TgInputMessageContent inputMessageContent;

    @Override
    public String toString() {
        return "TgInlineQueryResultArticle{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", inputMessageContent=" + inputMessageContent +
                '}';
    }
}
