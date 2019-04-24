package me.voidless.voidlib.tools.titles;

import me.voidless.voidlib.VoidLib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class TitleManager {

    private VoidLib voidLib;
    public HashMap<UUID ,Titles> titles;

    public TitleManager(final VoidLib voidLib){
        this.voidLib = voidLib;
        this.titles = new HashMap<>();
        titlesLoop();
    }

    public void addTitle(final Player player, final Title title, final boolean override){
        final Titles titles1 = titles.get(player.getUniqueId());
        if (titles1 != null){
            if (override) titles1.override(title);
            else titles1.next.add(title);
            return;
        }

        titles.put(player.getUniqueId(), new Titles(player.getUniqueId(), title));
    }

    public void stopLoop(final Player player){
        if (titles.containsKey(player.getUniqueId())) titles.get(player.getUniqueId()).stopLoop();
    }

    private void titlesLoop(){
        final BukkitTask task = new BukkitRunnable(){
            @Override
            public void run(){
                if (titles.isEmpty()) return;

                for (final Titles title : titles.values())
                    if (!title.running && (title.current == null || Bukkit.getPlayer(title.uuid) == null)) titles.remove(title.uuid);
            }
        }.runTaskTimer(voidLib, 0, 100);
    }
}
