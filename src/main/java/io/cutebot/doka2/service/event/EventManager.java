package io.cutebot.doka2.service.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventManager {
    private static Logger log = LoggerFactory.getLogger(EventManager.class);
    private Map<Class<? extends Event>, List<EventListener>> listeners = new HashMap<>();

    public void subscribe(EventListener listener) {
        Class<? extends Event> eventClass = getGenericParameterClass(listener.getClass());
        if (!listeners.containsKey(eventClass)) {
            listeners.put(eventClass, new ArrayList<>());
        }
        List<EventListener> eventListeners = this.listeners.get(eventClass);
        eventListeners.add(listener);
    }

    public void newEvent(Event event) {
        List<EventListener> eventListeners = this.listeners.get(event.getClass());
        log.debug("Event: {}", event);
        if (eventListeners != null) {
            for (EventListener listener : eventListeners) {
                listener.update(event);
            }
        }
    }

    private Class<? extends Event> getGenericParameterClass(Class<? extends EventListener> cls) {
        AnnotatedType[] types = cls.getAnnotatedInterfaces();
        for (AnnotatedType t : types) {
            if (!(t.getType() instanceof ParameterizedType)) {
                continue;
            }
            ParameterizedType type = (ParameterizedType)t.getType();
            if (!type.getRawType().equals(EventListener.class)) {
                continue;
            } else {
                return (Class) type.getActualTypeArguments()[0];
            }
        }
        return null;
    }

}
