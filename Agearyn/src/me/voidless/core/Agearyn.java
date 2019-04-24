package me.voidless.core;

import me.voidless.voidlib.tools.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Agearyn extends JavaPlugin {

    private Recipes recipes;
    private Ticks ticks;

    @Override
    public void onEnable(){
        loadEvents();
        this.recipes = new Recipes(this);
        this.ticks = new Ticks(this);
        MessageUtils.console("&f[&aAgearyn&f] Loading complete.");
    }

    private void loadEvents(){
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new Functions(this), this);
        pluginManager.registerEvents(new Display(), this);
    }

    public Recipes getRecipes() {
        return recipes;
    }

    public Ticks getTicks() {
        return ticks;
    }
}
