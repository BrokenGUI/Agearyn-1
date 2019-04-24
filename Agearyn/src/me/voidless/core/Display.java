package me.voidless.core;

import me.voidless.voidlib.events.player.ChatEvent;
import me.voidless.voidlib.events.player.JoinEvent;
import me.voidless.voidlib.events.player.LeaveEvent;
import me.voidless.voidlib.tools.MessageUtils;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_13_R2.IChatBaseComponent;
import net.minecraft.server.v1_13_R2.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Display implements Listener {

    /*
        Chat
        Join
        Leave
        Sidebar
        Tablist
     */

    // Chat
    @EventHandler
    public void onChat(ChatEvent event){
        final TextComponent textComponent = new TextComponent();
        if (!event.getMessage().startsWith("#")) MessageUtils.console("&a" + event.getPlayer().getName() + "&f: " + event.getMessage());

        // TODO group
        textComponent.addExtra("none");

        // Group split
        final TextComponent groupSplit = new TextComponent();
        groupSplit.addExtra(MessageUtils.color(" &8| "));

        // TODO marry

        // Name
        final TextComponent name = new TextComponent();
        name.addExtra(MessageUtils.color("&f" + event.getPlayer().getName()));

        // TODO title

        // Name split
        final TextComponent nameSplit = new TextComponent();
        nameSplit.addExtra(MessageUtils.color("&8: &f"));

        // Message
        final TextComponent message = new TextComponent();
        message.addExtra(MessageUtils.color(event.getMessage()));

        // Build
        textComponent.addExtra(groupSplit);
        textComponent.addExtra(name);
        textComponent.addExtra(nameSplit);
        textComponent.addExtra(message);

        // Return
        event.setTextComponent(textComponent);
    }

    // Join
    @EventHandler
    public void onJoin(JoinEvent event){
        final TextComponent textComponent = new TextComponent();
        textComponent.addExtra(MessageUtils.color("&a➡ &f" + event.getPlayer().getName()));

        // Apply tablist
        updateTablist();

        // Return
        event.setTextComponent(textComponent);
    }

    // Leave
    @EventHandler
    public void onLeave(LeaveEvent event){
        final TextComponent textComponent = new TextComponent();
        textComponent.addExtra(MessageUtils.color("&c➡ &f" + event.getPlayer().getName()));

        // Apply tablist
        updateTablist();

        // Return
        event.setTextComponent(textComponent);
    }

    // Sidebar


    // Tablist
    public void updateTablist(){
        final int online = online();
        final String header = MessageUtils.color("&a&lAgearyn &f- &9Custom survival" +
                "\n&bDiscord: &fDiscord.gg/eej4x2H" +
                "\n&8&l&m--------------------------------" + (online < 20 ? "" : "----------------"));
        final String footer = MessageUtils.color("\n&8&l&m--------------------------------" + (online < 20 ? "" : "----------------") +
                "\n&fThere are &a" + online + " &fplayer" + (online == 1 ? "" : "s") + " connect and &a" + staff() + " &fstaff connected.");

        final PacketPlayOutPlayerListHeaderFooter tabList = new PacketPlayOutPlayerListHeaderFooter();
        tabList.header = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + header + "\"}");
        tabList.footer = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + footer + "\"}");

        for (final Player player : Bukkit.getOnlinePlayers()){
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(tabList);
        }
    }

    private int online(){
        return Bukkit.getOnlinePlayers().size();
    }

    private int staff(){
        int staff = 0;
        for (final Player player : Bukkit.getOnlinePlayers()){
            if (player.isOp()) staff++;
        }

        return staff;
    }
}
