package io.cutebot.telegram.tgmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TgMessage {
    @JsonProperty("message_id")
    public Long messageId;

    @JsonProperty("from")
    public TgUser from;

    public Long date;

    public TgChatShort chat;

    @JsonProperty("forward_from")
    public TgUser forwardFrom;

    @JsonProperty("forward_from_chat")
    public TgChatShort forwardFromChat;

    @JsonProperty("forward_from_message_id")
    public Long forwardFromMessageId;

    @JsonProperty("forward_signature")
    public Long forwardSignature;

    @JsonProperty("forward_date")
    public Long forwardDate;

    @JsonProperty("reply_to_message")
    public TgMessage replyToMessage;

    @JsonProperty("edit_date")
    public Long editDate;

    @JsonProperty("media_group_id")
    public String mediaGroupId;

    @JsonProperty("author_signature")
    public String authorSignature;

    @JsonProperty("text")
    public String text;

    @JsonProperty("caption")
    public String caption;

    @Override
    public String toString() {
        return "TgMessage{" +
                "messageId=" + messageId +
                ", from=" + from +
                ", date=" + date +
                ", chat=" + chat +
                ", forwardFrom=" + forwardFrom +
                ", forwardFromChat=" + forwardFromChat +
                ", forwardFromMessageId=" + forwardFromMessageId +
                ", forwardSignature=" + forwardSignature +
                ", forwardDate=" + forwardDate +
                ", replyToMessage=" + replyToMessage +
                ", editDate=" + editDate +
                ", mediaGroupId='" + mediaGroupId + '\'' +
                ", authorSignature='" + authorSignature + '\'' +
                ", text='" + text + '\'' +
                ", caption='" + caption + '\'' +
                '}';
    }
}
