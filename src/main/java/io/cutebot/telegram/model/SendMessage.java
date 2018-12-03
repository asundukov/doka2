package io.cutebot.telegram.model;

import io.cutebot.doka2.area.Area;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

public final class SendMessage {
    private final List<SendMessageItem> items;
    private final Area redirectArea;
    private final Object redirectData;

    private SendMessage(List<SendMessageItem> items) {
        this.items = items;
        this.redirectArea = null;
        redirectData = null;
    }

    private SendMessage(Area redirectArea, Object data) {
        this.items = Collections.emptyList();
        this.redirectArea = redirectArea;
        this.redirectData = data;
    }

    private SendMessage(Area redirectArea) {
        this(redirectArea, "");
    }

    public static SendMessage items(SendMessageItem... items) {
        return new SendMessage(asList(items));
    }

    public static SendMessage redirect(Area redirectArea) {
        return redirect(redirectArea, "");
    }

    public static SendMessage redirect(Area redirectArea, Object data) {
        return new SendMessage(redirectArea, data);
    }

    public List<SendMessageItem> getItems() {
        return items;
    }

    public boolean isRedirect() {
        return redirectArea != null;
    }

    public Area getRedirectArea() {
        return redirectArea;
    }

    public Object getRedirectData() {
        return redirectData;
    }
}
