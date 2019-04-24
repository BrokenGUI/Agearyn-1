package me.voidless.voidlib.tools.titles;

import me.voidless.voidlib.VoidLib;
import me.voidless.voidlib.tools.MessageUtils;
import net.minecraft.server.v1_13_R2.IChatBaseComponent;
import net.minecraft.server.v1_13_R2.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Titles {

    public UUID uuid;
    public Title current;
    public List<Title> next;
    public boolean running;

    public Titles(final UUID uuid, final Title title){
        this.uuid = uuid;
        this.current = title;
        this.next = new ArrayList<>();
        this.running = true;

        display();
        tick();
    }

    public void override(final Title title){
        this.current = title;
        display();
    }

    public void stopLoop(){
        if (!current.loop) return;
        current.loop = false;
        current.reset();
    }

    private void tick(){
        final BukkitTask task = new BukkitRunnable(){
            @Override
            public void run(){
                if (current == null) {
                    running = false;
                    this.cancel();
                    return;
                }

                tickTask();
            }
        }.runTaskTimer(VoidLib.get(), 0, 1);
    }

    private void tickTask(){
        if (current.left <= 0){
            if (next.isEmpty()) return;

            if (current.loop){
                current.reset();
                display();
                return;
            }

            for (int i = 0; i < next.size(); i++) {
                final Title title = next.get(i);
                if (title != null && title.text != null) {
                    current = title;
                    next.remove(i);
                    display();
                    break;
                }
            }
        }

        current.left = current.left - 1;
    }

    private void display(){
        final Player player = Bukkit.getPlayer(uuid);
        if (player == null){
            next.clear();
            current = null;
            return;
        }

        if (current.type == TitleType.ACTIONBAR){
            final PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.ACTIONBAR, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" +
                    MessageUtils.color(current.text) + "\"}"));
            final PacketPlayOutTitle length = new PacketPlayOutTitle(current.fadeIn, current.stay, current.loop ? 0 : current.fadeOut);

            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
        } else if (current.type == TitleType.TITLE){
            final PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" +
                    MessageUtils.color(current.text) + "\"}"));
            final PacketPlayOutTitle subTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" +
                    MessageUtils.color(current.subText) + "\"}"));
            final PacketPlayOutTitle length = new PacketPlayOutTitle(current.fadeIn, current.stay, current.loop ? 0 : current.fadeOut);

            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subTitle);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
        }
    }
}
