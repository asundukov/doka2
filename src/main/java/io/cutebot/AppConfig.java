package io.cutebot;

import io.cutebot.doka2.jersey.mapper.BadRequestWebExceptionMapper;
import io.cutebot.doka2.jersey.mapper.ConstraintViolationMapper;
import io.cutebot.doka2.jersey.mapper.ForbiddenWebExceptionMapper;
import io.cutebot.doka2.jersey.mapper.IllegalArgumentWebExceptionMapper;
import io.cutebot.doka2.jersey.mapper.JsonMappingExceptionMapper;
import io.cutebot.doka2.jersey.mapper.JsonParseExceptionMapper;
import io.cutebot.doka2.jersey.mapper.NotFoundWebExceptionMapper;
import io.cutebot.doka2.jersey.mapper.WebExceptionMapper;
import io.cutebot.doka2.service.BotService;
import io.cutebot.telegram.handlers.LongPollProcess;
import io.cutebot.telegram.handlers.WebhookListener;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.ws.rs.core.Context;
import java.util.Calendar;

@Configuration
@PropertySource("file:${settingsDir}/application.properties")
public class AppConfig extends ResourceConfig {
    public static int DEFAULT_TIME_UNIT = Calendar.MINUTE;

    private Logger log = LoggerFactory.getLogger(AppConfig.class);

    @Value("${telegram.longpoll.enable}")
    private Boolean longPollEnable;

    public AppConfig(@Context ConfigurableApplicationContext context) {
        register(ForbiddenWebExceptionMapper.class);
        register(NotFoundWebExceptionMapper.class);
        register(BadRequestWebExceptionMapper.class);
        register(WebExceptionMapper.class);
        register(IllegalArgumentWebExceptionMapper.class);

        register(ConstraintViolationMapper.class);
        register(JsonParseExceptionMapper.class);
        register(JsonMappingExceptionMapper.class);

        register(WebhookListener.class);
    }

    public void startBots(ConfigurableApplicationContext context) {
        if (longPollEnable) {
            context.getBean(BotService.class).stopWithWebhook();
            context.getBean(LongPollProcess.class).start();
        } else {
            context.getBean(BotService.class).startWithWebhook();
        }
    }

}
