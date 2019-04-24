package me.voidless.voidlib.events.player;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class ChatEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    private Player player;
    private String message;
    private TextComponent textComponent;

    public ChatEvent(final Player player, final String message) {
        this.cancelled = false;
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

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
