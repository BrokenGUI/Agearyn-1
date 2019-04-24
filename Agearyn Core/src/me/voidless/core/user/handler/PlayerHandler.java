package me.voidless.core.user.handler;

import me.voidless.core.AgearynCore;
import me.voidless.core.mongo.MongoHandler;
import me.voidless.core.user.manager.UserManagerOption;
import me.voidless.core.user.user.User;
import me.voidless.voidlib.tools.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class PlayerHandler implements Listener {

    private final AgearynCore agearynCore;

    public PlayerHandler(final AgearynCore agearynCore){
        this.agearynCore = agearynCore;
        timeLoop();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        final Player player = event.getPlayer();
        final User user = agearynCore.getUserManager().getUser(player.getUniqueId());

        if (user == null) player.kickPlayer(MessageUtils.color("&cError: Could not load the user. (&f" + agearynCore.getMongoHandler().connected() + "&c, &f" + (user != null) + "&c)"));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        final User user = agearynCore.getUserManager().getUser(event.getPlayer().getUniqueId());
        if (user != null) {
            agearynCore.getUserManager().saveUser(user);
            agearynCore.getUserManager().updateUser(user, UserManagerOption.OFFLINE);
        }
    }

    private void timeLoop(){
        final BukkitTask task = new BukkitRunnable(){
            int minutes = 0;

            @Override
            public void run(){
                for (final User user : agearynCore.getUserManager().online.values()){
                    user.statistics.totalPlaytime++;
                    user.statistics.currentPlaytime++;
                }

                minutes++;
                if (minutes == 30){
                    minutes = 0;

                    agearynCore.getUserManager().saveUsers();
                    final MongoHandler mongoHandler = agearynCore.getMongoHandler();
                    mongoHandler.disconnect();
                    mongoHandler.connect();
                }
            }
        }.runTaskTimer(AgearynCore.get(), 0, (60) * 20);
    }
}
