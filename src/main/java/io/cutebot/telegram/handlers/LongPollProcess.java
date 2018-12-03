package io.cutebot.telegram.handlers;

import io.cutebot.doka2.service.BotService;
import io.cutebot.telegram.tgmodel.TgResponseUpdate;
import io.cutebot.telegram.tgmodel.TgUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class LongPollProcess extends Thread {

    private static final Logger log = LoggerFactory.getLogger(LongPollProcess.class);

    @Value("${telegram.longpoll.timeout}")
    private Integer longPollTimeout;

    @Inject
    private BotService botService;

    private boolean ok = true;

    public void run() {
        int offset = 0;

        while (ok) {
            try {
                TgResponseUpdate tgResponseUpdate = botService.getMessages(offset, longPollTimeout, 50);
                if (!ok) {
                    return;
                }
                for (TgUpdate tgUpdate : tgResponseUpdate.result) {
                    offset = tgUpdate.updateId + 1;
                    botService.handle(tgUpdate);
                }
                if (Thread.interrupted()) {
                    ok = false;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    ok = false;
                }
            }
        }
    }

}
