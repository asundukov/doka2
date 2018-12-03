package io.cutebot.telegram.handlers;

import io.cutebot.doka2.service.BotService;
import io.cutebot.telegram.tgmodel.TgUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/webhook")
@Produces(APPLICATION_JSON)
@Service
public class WebhookListener {

    private Logger log = LoggerFactory.getLogger(WebhookListener.class);

    @Value("${bot.token}")
    private String botToken;

    @Inject
    private BotService botService;

    @POST
    @Path("/{token}")
    public Object update(@PathParam("token") String token, @Valid @NotNull TgUpdate update) {
        if (!botToken.equals(token)) {
            log.warn("unregistered webhook call from bot {}", token, update);
            return null;
        }

        botService.handle(update);
        return "ok";
    }

}
