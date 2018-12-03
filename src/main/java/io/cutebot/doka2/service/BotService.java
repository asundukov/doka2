package io.cutebot.doka2.service;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.model.inline.InlineAnswer;
import io.cutebot.telegram.TelegramService;
import io.cutebot.telegram.model.SendMessage;
import io.cutebot.telegram.model.SendMessageItem;
import io.cutebot.telegram.tgmodel.TgMessage;
import io.cutebot.telegram.tgmodel.TgResponseUpdate;
import io.cutebot.telegram.tgmodel.TgSendPhoto;
import io.cutebot.telegram.tgmodel.TgSendTextMessage;
import io.cutebot.telegram.tgmodel.TgUpdate;
import io.cutebot.telegram.tgmodel.inline.TgAnswerInlineQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;


@Service
public class BotService {
    private static final Logger log = LoggerFactory.getLogger(BotService.class);

    @Inject
    private TelegramService telegramService;

    @Inject
    private TextHandler textHandler;

    @Inject
    private InlineHandler inlineHandler;

    @Inject
    private HeroFactory usrFactory;

    public void handle(TgUpdate update) {
        log.info("handle message {}", update);

        if (update.message != null) {
            Hero hero = usrFactory.getHero(update.message.from);
            sendAllMessages(textHandler.handle(hero, update.message));
            return;
        }
        if (update.inlineQuery != null) {
            Hero hero = usrFactory.getHero(update.inlineQuery.from);
            answerInline(inlineHandler.handle(hero, update.inlineQuery));
            return;
        }
        if (update.chosenInlineResult != null) {
            log.info("Chosen inline {}", update.chosenInlineResult);
            return;
        }
        log.error("Unexpected update object {}", update);
    }

    public void sendAllMessages(SendMessage sendMessage) {
        for (SendMessageItem item : sendMessage.getItems()) {
            sendMessage(item);
        }
    }

    public TgMessage sendMessage(SendMessageItem sendMessageItem) {
        TgMessage tgMessage;
        if (sendMessageItem.isPhoto()) {
            TgSendPhoto photo = sendMessageItem.toTgPhoto();
            log.info("Send photo: {}", photo);
            tgMessage = telegramService.sendPhoto(photo);
        } else {
            TgSendTextMessage msg = sendMessageItem.toTgMessage();
            log.info("Send text msg: {}", msg);
            tgMessage = telegramService.sendMessage(msg);
        }
        return tgMessage;
    }

    public TgResponseUpdate getMessages(int offset, Integer timeout, int limit) {
        return telegramService.getUpdates(offset, limit, timeout);
    }

    public void stopWithWebhook() {
        telegramService.deleteWebhook();
    }

    public void startWithWebhook() {
        telegramService.setWebhook();
    }

    public void answerInline(InlineAnswer inlineAnswer) {
        TgAnswerInlineQuery answer = inlineAnswer.toInlineAnswer();
        log.info("Inline answer: {}", answer);
        telegramService.answerInlineQuery(answer);
    }
}
