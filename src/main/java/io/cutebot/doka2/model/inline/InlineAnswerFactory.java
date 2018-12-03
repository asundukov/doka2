package io.cutebot.doka2.model.inline;

import io.cutebot.doka2.Hero;
import org.springframework.stereotype.Service;

@Service
public class InlineAnswerFactory {
    public InlineAnswer createSendInline(Hero hero) {
        return new InlineAnswer(hero);
    }
}
