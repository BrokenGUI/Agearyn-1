package me.voidless.core;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Date;

public class Ticks {

    private final Agearyn agearyn;
    public long ticksPassed;
    public Long started;

    public Ticks(final Agearyn agearyn){
        this.agearyn = agearyn;
        this.ticksPassed = 0;
        this.started = new Date().getTime();
        tickLoop();
    }

    public long passed(){
        return ((new Date().getTime() / 1000) - (started / 1000)) * 20;
    }

    private void tickLoop(){
        final BukkitTask task = new BukkitRunnable(){
            @Override
            public void run() {
                ticksPassed++;
            }
        }.runTaskTimer(agearyn, 0, 1);
    }

}
