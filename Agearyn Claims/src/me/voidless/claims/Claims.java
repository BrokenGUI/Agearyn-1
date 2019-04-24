package me.voidless.claims;

import org.bukkit.plugin.java.JavaPlugin;

public class Claims extends JavaPlugin {

    private ClaimManager claimManager;

    @Override
    public void onEnable(){
        this.claimManager = new ClaimManager();
    }

    public ClaimManager getClaimManager() {
        return claimManager;
    }

    public static Claims get(){
        return Claims.getPlugin(Claims.class);
    }
}
