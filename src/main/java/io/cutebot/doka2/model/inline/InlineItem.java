package io.cutebot.doka2.model.inline;

import io.cutebot.telegram.tgmodel.inline.TgInlineQueryResult;

public abstract class InlineItem {
    public abstract TgInlineQueryResult toTgInlineResult(String id);
}
