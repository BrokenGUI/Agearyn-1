package me.voidless.voidlib.events;

import me.voidless.voidlib.VoidLib;
import me.voidless.voidlib.events.world.DayEvent;
import me.voidless.voidlib.events.world.NightEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TimeHandler {

    private VoidLib voidLib;

    public TimeHandler(final VoidLib voidLib){
        this.voidLib = voidLib;
        timeLoop();
    }

    private void timeLoop(){
        final BukkitTask task = new BukkitRunnable(){
            World world = Bukkit.getWorld("world");

            private boolean day = false;
            @Override
            public void run(){
                if (world.getTime() >= 0 && world.getTime() < 12566 && !day){
                    Bukkit.getServer().getPluginManager().callEvent(new DayEvent());
                    day = true;
                }

                if (world.getTime() >= 12566 && day){
                    Bukkit.getServer().getPluginManager().callEvent(new NightEvent());
                    day = false;
                }
            }
        }.runTaskTimerAsynchronously(voidLib, 0, 60);
    }
}
