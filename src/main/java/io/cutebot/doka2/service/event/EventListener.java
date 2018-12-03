package io.cutebot.doka2.service.event;

public interface EventListener<T extends Event> {
    void update(T event);
}
