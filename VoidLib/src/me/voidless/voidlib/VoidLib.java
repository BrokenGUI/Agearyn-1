package me.voidless.voidlib;

import me.voidless.voidlib.events.EventHandler;
import me.voidless.voidlib.events.TimeHandler;
import me.voidless.voidlib.tools.MessageUtils;
import me.voidless.voidlib.tools.titles.TitleManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class VoidLib extends JavaPlugin {

    private TitleManager titleManager;
    private TimeHandler timeHandler;

    @Override
    public void onEnable(){
        loadEvents();
        this.titleManager = new TitleManager(this);
        this.timeHandler = new TimeHandler(this);
        MessageUtils.console("&f[&5Void&8Lib&f] Loading complete.");
    }

    private void loadEvents(){
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new EventHandler(), this);
    }

    public TitleManager getTitleManager() {
        return titleManager;
    }

    public TimeHandler getTimeHandler() {
        return timeHandler;
    }

    public static VoidLib get(){
        return VoidLib.getPlugin(VoidLib.class);
    }
}
