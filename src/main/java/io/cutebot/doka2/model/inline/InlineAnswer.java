package io.cutebot.doka2.model.inline;

import io.cutebot.doka2.Hero;
import io.cutebot.telegram.tgmodel.inline.TgAnswerInlineQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InlineAnswer {
    private Hero hero;
    private List<InlineItem> items = new ArrayList<>();

    private String inlinePmText;
    private String inlinePmParameter;
    private String inlineQueryId;

    public InlineAnswer(Hero hero) {
        this.hero = hero;
    }

    public TgAnswerInlineQuery toInlineAnswer() {
        TgAnswerInlineQuery query = new TgAnswerInlineQuery();
        query.inlineQueryId = inlineQueryId;
        query.switchPmText = inlinePmText;
        query.switchPmParameter = inlinePmParameter;
        Integer id = 1;
        for (InlineItem item : items) {
            query.results.add(item.toTgInlineResult(id.toString()));
            id++;
        }
        return query;
    }

    public void setInlinePmText(String inlinePmText) {
        this.inlinePmText = inlinePmText;
    }

    public void setInlinePmParameter(String p) {
        this.inlinePmParameter = p;
    }

    public void setInlineQueryId(String inlineQueryId) {
        this.inlineQueryId = inlineQueryId;
    }

    public void addAnswer(InlineItem inlineItem) {
        this.items.add(inlineItem);
    }

    public void addAllAnswers(Collection<InlineItem> inlineItems) {
        this.items.addAll(inlineItems);
    }
}
