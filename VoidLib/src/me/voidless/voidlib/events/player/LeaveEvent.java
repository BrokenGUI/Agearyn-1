package me.voidless.voidlib.events.player;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LeaveEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private String message;
    private TextComponent textComponent;

    public LeaveEvent(final Player player, final String message) {
        this.player = player;
        this.message = message;
        this.textComponent = null;
    }

    public Player getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }

    public TextComponent getTextComponent() {
        return textComponent;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setTextComponent(final TextComponent textComponent) {
        this.textComponent = textComponent;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}