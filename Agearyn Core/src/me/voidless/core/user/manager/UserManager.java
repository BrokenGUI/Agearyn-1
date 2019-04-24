package me.voidless.core.user.manager;

import me.voidless.core.AgearynCore;
import me.voidless.core.user.handler.UserHandler;
import me.voidless.core.user.user.User;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class UserManager {

    public HashMap<String, UUID> names;
    private UserHandler userHandler;
    public HashMap<UUID, User> online;
    public HashMap<UUID, User> offline;

    public UserManager(final AgearynCore agearyn){
        this.names = new HashMap<>();
        this.userHandler = new UserHandler(agearyn);
        this.online = new HashMap<>();
        this.offline = new HashMap<>();
        clearOffline();
    }

    public HashMap<UUID, User> getMap(final UUID uuid){
        if (online.containsKey(uuid)) return online;
        else if (offline.containsKey(uuid)) return offline;
        else return null;
    }

    public UserManagerResult updateUser(final User user, final UserManagerOption option){
        if (option == UserManagerOption.ONLINE) {
            offline.remove(user.uuid);
            online.put(user.uuid, user);
            return UserManagerResult.ACTION_SUCCESS;
        }

        else if (option == UserManagerOption.OFFLINE) {
            online.remove(user.uuid);
            offline.put(user.uuid, user);
            return UserManagerResult.ACTION_SUCCESS;
        }

        final HashMap<UUID, User> map = getMap(user.uuid);
        if (map == null) return UserManagerResult.NOT_FOUND;

        if (option == UserManagerOption.REMOVE){
            online.remove(user.uuid);
            offline.remove(user.uuid);
            return UserManagerResult.ACTION_SUCCESS;
        }

        return UserManagerResult.ACTION_FAILED;
    }

    private void clearOffline(){
        final BukkitTask task = new BukkitRunnable(){
            @Override
            public void run(){
                final HashMap<UUID, User> offlineCopy = new HashMap<>(offline);

                names.clear();
                for (final User user : offlineCopy.values()){
                    if (Bukkit.getPlayer(user.uuid) == null) removeUser(user);
                }
            }
        }.runTaskTimer(AgearynCore.get(), 0, 72000);
    }

    public UserManagerResult removeUser(final User user){
        userHandler.saveUser(user);
        return updateUser(user, UserManagerOption.REMOVE);
    }

    public void saveUser(final User user){
        userHandler.saveUser(user);
    }

    public void saveUsers(){
        for (final User user : online.values()){
            saveUser(user);
        }

        for (final User user : offline.values()){
            saveUser(user);
        }
    }

    public User getUser(final UUID uuid){
        if (online.containsKey(uuid)) return online.get(uuid);
        if (offline.containsKey(uuid)) return offline.get(uuid);

        final User user = userHandler.getUser(uuid);
        if (user != null){
            if (Bukkit.getPlayer(uuid) != null) online.put(uuid, user);
            else offline.put(uuid, user);
        }

        return user;
    }
}
