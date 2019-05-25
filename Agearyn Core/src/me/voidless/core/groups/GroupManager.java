package me.voidless.core.groups;

import me.voidless.core.AgearynCore;
import me.voidless.core.mongo.MongoCollectionHandler;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GroupManager {

    public boolean edited;
    private AgearynCore agearynCore;
    private ArrayList<String> groupsToSync;
    private GroupHandler groupHandler;
    public HashMap<String, Group> groups;

    public GroupManager(final AgearynCore agearynCore){
        this.edited = false;
        this.agearynCore = agearynCore;
        this.groupsToSync = loadSync();
        this.groupHandler = new GroupHandler();
        this.groups = new HashMap<>();

        final Group defaultGroup = new Group("default");
        defaultGroup.name = "&7Default";
        groups.put("default", defaultGroup);
        syncGroups();
    }

    private void syncGroups(){
        final BukkitTask task = new BukkitRunnable(){
            @Override
            public void run(){
                for (final String groupid : groupsToSync){
                    if (!groups.containsKey(groupid)){
                        if (loadGroup(groupid) == null) groupsToSync.remove(groupid);
                    }
                }

                if (edited){
                    for (final Group group : groups.values()){
                        updateGroup(group);
                    }
                }else{
                    final MongoCollectionHandler collectionHandler = new MongoCollectionHandler().createCollection("Agearyn", "Groups");
                    for (final Group group : groups.values()){
                        if (!collectionHandler.documentExist(group.id)) updateGroup(group);
                        loadGroup(group.id);
                    }
                }
            }
        }.runTaskTimer(agearynCore, 0, 1200);
    }

    public void addGroup(final Group group){
        if (!groups.containsKey(group.id)) groups.put(group.id, group);
        if (!groupsToSync.contains(group.id)) groupsToSync.add(group.id);
    }

    public Group getGroup(final String groupid){
        final Group group = groups.get(groupid);
        if (group != null) return group;

        if (!agearynCore.getMongoHandler().connected()) return null;
        return loadGroup(groupid);
    }

    public void updateAll(){
        for (final Group group : groups.values()){
            updateGroup(group);
        }
    }

    private Group loadGroup(final String groupid){
        final Group group = groupHandler.loadGroup(groupid);
        if (group != null && !groups.containsKey(groupid)) groups.put(groupid, group);
        return group;
    }

    private void updateGroup(final Group group){
        groupHandler.updateGroup(group);
    }

    public void saveSync(){
        final File file = new File(agearynCore.getDataFolder(), "groups.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        configuration.set("toLoad", groupsToSync);

        try{
            configuration.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private ArrayList<String> loadSync(){
        final File file = new File(agearynCore.getDataFolder(), "groups.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        return (ArrayList<String>) configuration.getList("toLoad");
    }
}
