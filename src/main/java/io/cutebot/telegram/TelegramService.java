package io.cutebot.telegram;

import io.cutebot.telegram.exception.TgBotNotFoundException;
import io.cutebot.telegram.handlers.SetWebhookDto;
import io.cutebot.telegram.tgmodel.TgMessage;
import io.cutebot.telegram.tgmodel.TgResponseUpdate;
import io.cutebot.telegram.tgmodel.TgSendPhoto;
import io.cutebot.telegram.tgmodel.TgSendTextMessage;
import io.cutebot.telegram.tgmodel.inline.TgAnswerInlineQuery;
import io.cutebot.telegram.tgmodel.response.TgResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;

@Service
public class TelegramService {

    private static final Logger log = LoggerFactory.getLogger(TelegramService.class);

    private final String webhookUrl;
    private final String token;

    private RestTemplate restTemplate;

    @Inject
    public TelegramService(
            @Value("${telegram.webhook.url}") String webhookUrl,
            @Value("${bot.token}") String token
    ) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(15000);
        factory.setReadTimeout(65000);
        restTemplate = new RestTemplate(factory);
        this.webhookUrl = webhookUrl;
        this.token = token;
    }

    public TgResponseUpdate getUpdates(int offset, int limit, int timeout) {
        String method = "getUpdates?offset=" + offset + "&limit=" + limit + "&timeout=" + timeout;
        try {
            return getMethod(method, TgResponseUpdate.class);
        } catch (HttpClientErrorException e) {
            if (e.getRawStatusCode() == 401) {
                throw new TgBotNotFoundException();
            }
            throw new RuntimeException(e);
        }
    }

    public TgMessage sendPhoto(TgSendPhoto sendPhoto) {
        return postMethod(sendPhoto, "sendPhoto", TgResponseMessage.class).result;
    }

    public TgMessage sendMessage(TgSendTextMessage sendMessage) {
        try {
            return postMethod(sendMessage, "sendMessage", TgResponseMessage.class).result;
        } catch (HttpClientErrorException e) {
            log.warn("error during sendMessage", e);
            if (e.getRawStatusCode() == 403) {
                log.info("Blocked by user: {}", e.getResponseBodyAsString());
            }
            if (e.getRawStatusCode() == 400) {
                log.info("Chat not found: {}", e.getResponseBodyAsString());
            }
            throw e;
        }
    }

    public TgMessage updateMessage(TgSendTextMessage tgSendMessage, Long updateMessageId) {
        tgSendMessage.messageId = updateMessageId;
        try {
            return postMethod(tgSendMessage, "editMessageText", TgResponseMessage.class).result;
        } catch (HttpClientErrorException e) {
            log.warn("error during editMessageText", e);
            if (e.getRawStatusCode() == 403) {
                log.info("Blocked by user: {}", e.getResponseBodyAsString());
            }
            if (e.getRawStatusCode() == 400) {
                log.info("Chat not found: {}", e.getResponseBodyAsString());
            }
            throw e;
        }

    }

    public void answerInlineQuery(TgAnswerInlineQuery tgAnswerInlineQuery) {
        try {
            postMethod(tgAnswerInlineQuery, "answerInlineQuery", String.class);
        } catch (HttpClientErrorException e) {
            log.warn("error during answerInlineQuery", e);
            if (e.getRawStatusCode() == 403) {
                log.info("Blocked by user: {}", e.getResponseBodyAsString());
            }
            if (e.getRawStatusCode() == 400) {
                log.info("Chat not found: {}", e.getResponseBodyAsString());
            }
            throw e;
        }
    }

    public void setWebhook() {
        SetWebhookDto setWebhookDto = new SetWebhookDto();
        setWebhookDto.url = webhookUrl + "/webhook/" + token;
        postMethod(setWebhookDto, "setWebhook", Void.class);
    }

    public void deleteWebhook() {
        getMethod("deleteWebhook", Void.class);
    }

    private <T> T postMethod(Object request, String methodName, Class<T> clazz) {
        String url = "https://api.telegram.org/bot" + token + "/" + methodName;
        log.info("POST {} to {}", request, url);
        try {
            T response = restTemplate.postForEntity(url, request, clazz).getBody();
            log.info("POST {} RESPONSE {}", methodName, response);
            return response;
        } catch (HttpClientErrorException e) {
            log.info(e.getResponseBodyAsString());
            throw e;
        }
    }

    private <T> T getMethod(String methodName, Class<T> clazz) {
        String url = "https://api.telegram.org/bot" + token + "/" + methodName;
        log.info("GET {}", url);
        try {
            T response = restTemplate.getForEntity(url, clazz).getBody();
            log.info("GET {} RESPONSE {}", methodName, response);
            return response;
        } catch (HttpClientErrorException e) {
            log.info("Error telegram api. GET {}. Response {}", url, e.getResponseBodyAsString());
            throw e;
        }
    }

}
