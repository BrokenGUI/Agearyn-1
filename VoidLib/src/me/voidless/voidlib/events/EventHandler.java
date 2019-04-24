package me.voidless.voidlib.events;

import me.voidless.voidlib.events.player.ChatEvent;
import me.voidless.voidlib.events.player.JoinEvent;
import me.voidless.voidlib.events.player.LeaveEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventHandler implements Listener {

    /*
        ChatEvent
        JoinEvent
        LeaveEvent
     */

    // ChatEvent
    @org.bukkit.event.EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        final ChatEvent chatEvent = new ChatEvent(event.getPlayer(), event.getMessage());
        Bukkit.getPluginManager().callEvent(chatEvent);

        if (chatEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        if (chatEvent.getTextComponent() == null){
            event.setMessage(chatEvent.getMessage());
            return;
        }

        event.setCancelled(true);
        final TextComponent textComponent = chatEvent.getTextComponent();
        for (final Player player : Bukkit.getOnlinePlayers()){
            player.spigot().sendMessage(textComponent);
        }
    }

    // JoinEvent
    @org.bukkit.event.EventHandler
    public void onJoin(PlayerJoinEvent event){
        final JoinEvent joinEvent = new JoinEvent(event.getPlayer(), event.getJoinMessage());
        Bukkit.getPluginManager().callEvent(joinEvent);

        if (joinEvent.getTextComponent() == null){
            event.setJoinMessage(joinEvent.getMessage());
            return;
        }

        event.setJoinMessage(null);
        final TextComponent textComponent = joinEvent.getTextComponent();
        for (final Player player : Bukkit.getOnlinePlayers()){
            player.spigot().sendMessage(textComponent);
        }
    }

    // LeaveEvent
    @org.bukkit.event.EventHandler
    public void onLeave(PlayerQuitEvent event){
        final LeaveEvent leaveEvent = new LeaveEvent(event.getPlayer(), event.getQuitMessage());
        Bukkit.getPluginManager().callEvent(leaveEvent);

        if (leaveEvent.getTextComponent() == null){
            event.setQuitMessage(leaveEvent.getMessage());
            return;
        }

        event.setQuitMessage(null);
        final TextComponent textComponent = leaveEvent.getTextComponent();
        for (final Player player : Bukkit.getOnlinePlayers()){
            player.spigot().sendMessage(textComponent);
        }
    }
}
