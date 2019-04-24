package me.voidless.core.user.user;

import org.bukkit.entity.Player;

import java.util.UUID;

public class User {

    public UUID uuid;
    public String name;
    public String group;

    //Classes
    public Moderation moderation;
    public Statistics statistics;
    public Skills skills;

    public User(){
        this.uuid = null;
        this.name = null;
        setup();
    }

    public User(final Player player){
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        setup();
    }

    private void setup(){
        this.group = null;
        this.moderation = new Moderation();
        this.statistics = new Statistics();
        this.skills = new Skills();
    }
}
