package me.voidless.core;

import me.voidless.core.groups.GroupManager;
import me.voidless.core.mongo.MongoHandler;
import me.voidless.core.user.handler.PlayerHandler;
import me.voidless.core.user.manager.UserManager;
import me.voidless.voidlib.tools.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AgearynCore extends JavaPlugin {

    private MongoHandler mongoHandler;
    private UserManager userManager;
    private GroupManager groupManager;

    @Override
    public void onEnable(){
        this.mongoHandler = new MongoHandler().connect(this);
        this.userManager = new UserManager(this);
        this.groupManager = new GroupManager(this);
        loadEvents();
        MessageUtils.console("&f[&aAgearyn &bcore&f] Loading complete.");
    }

    @Override
    public void onDisable(){
        this.mongoHandler.saveUri();
        this.userManager.saveUsers();
        this.groupManager.updateAll();
        this.groupManager.saveSync();
    }

    private void loadEvents(){
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerHandler(this), this);
    }

    public MongoHandler getMongoHandler(){
        return mongoHandler;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

    public void fixHandler(){
        this.mongoHandler = new MongoHandler().connect(this);
    }

    public static AgearynCore get(){
        return AgearynCore.getPlugin(AgearynCore.class);
    }
}
