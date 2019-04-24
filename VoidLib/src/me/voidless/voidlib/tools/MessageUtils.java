package me.voidless.voidlib.tools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtils {

    public static void console(final String message){
        Bukkit.getConsoleSender().sendMessage(color(message));
    }

    public static void player(final Player player, final String message){
        player.sendMessage(color(message));
    }

    public static String color(final String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
