package io.cutebot.doka2.service;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.model.inline.InlineAnswer;
import io.cutebot.doka2.model.inline.InlineAnswerFactory;
import io.cutebot.telegram.tgmodel.inline.TgInlineQuery;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;

@Service
public class InlineHandler {

    @Inject
    private InlineAnswerFactory answerFactory;

    public InlineAnswer handle(Hero hero, TgInlineQuery inlineQuery) {

        InlineAnswer inlineAnswer = answerFactory.createSendInline(hero);
        inlineAnswer.setInlinePmText("Add addresses subscribes to easy access");
        inlineAnswer.setInlinePmParameter("blaa");
        inlineAnswer.setInlineQueryId(inlineQuery.id);

        inlineAnswer.addAllAnswers(Collections.emptyList());

        return inlineAnswer;
    }

}
