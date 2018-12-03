package io.cutebot.telegram.handlers;

public class SetWebhookDto {
    public String url;

    @Override
    public String toString() {
        return "SetWebhookDto{" +
                "url='" + url + '\'' +
                '}';
    }
}
