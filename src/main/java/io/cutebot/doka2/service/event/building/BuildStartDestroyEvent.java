package io.cutebot.doka2.service.event.building;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.model.Build;
import io.cutebot.doka2.service.event.Event;

public class BuildStartDestroyEvent extends Event {
    public Build build;
    public Hero destroyer;
}
