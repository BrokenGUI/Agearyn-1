package me.voidless.voidlib.events.world;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DayEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public DayEvent() {}

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}