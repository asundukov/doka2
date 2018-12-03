package io.cutebot.doka2.service;

import io.cutebot.telegram.model.SendMessage;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class InitiateHandler{
    @Inject
    private BotService botService;

    public void initiate(SendMessage sendMessage) {
        botService.sendAllMessages(sendMessage);
    }
}
