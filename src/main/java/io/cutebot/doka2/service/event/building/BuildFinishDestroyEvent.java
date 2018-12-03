package io.cutebot.doka2.service.event.building;

import io.cutebot.doka2.model.Build;
import io.cutebot.doka2.service.event.Event;

public class BuildFinishDestroyEvent extends Event {
    public Build destroyedBuild;
}
