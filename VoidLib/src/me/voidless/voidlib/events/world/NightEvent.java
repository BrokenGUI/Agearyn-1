package me.voidless.voidlib.events.world;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NightEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public NightEvent() {}

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}