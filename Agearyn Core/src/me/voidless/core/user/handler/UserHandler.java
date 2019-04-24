package me.voidless.core.user.handler;

import me.voidless.core.AgearynCore;
import me.voidless.core.mongo.MongoCollectionHandler;
import me.voidless.core.mongo.MongoHandler;
import me.voidless.core.user.user.Moderation;
import me.voidless.core.user.user.Statistics;
import me.voidless.core.user.user.User;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UserHandler {

    final AgearynCore agearynCore;

    public UserHandler(final AgearynCore agearynCore){
        this.agearynCore = agearynCore;
    }

    // Load user
    public User getUser(final UUID uuid){
        final MongoHandler mongoHandler = agearynCore.getMongoHandler();

        if (!mongoHandler.connected()) return null;
        return load(uuid, mongoHandler);
    }

    private User load(final UUID uuid, final MongoHandler mongoHandler){
        if (!mongoHandler.connected()) return null;
        final File file = new File(agearynCore.getDataFolder(), "users/" + uuid + "/player.yml");
        if (file.exists()) return loadYML(uuid, file);

        final MongoCollectionHandler collectionHandler = new MongoCollectionHandler().createCollection("Agearyn", "Users");
        final User user = new User();
        user.uuid = uuid;

        if (!collectionHandler.documentExist(uuid.toString())) {
            final Player player = Bukkit.getPlayer(uuid);
            if (player != null){
                user.name = player.getName();
                return user;
            }

            return null;
        }

        final Document document = collectionHandler.documentGet(uuid.toString());
        final Player player = Bukkit.getPlayer(uuid);
        user.name = player != null ? player.getName() : (document != null ? document.getString("username") : null);
        if (document == null) return user;

        if (document.containsKey("group")) user.group = document.getString("group");

        final Moderation moderation = user.moderation;
        if (document.containsKey("moderation-staffMode")) moderation.staffMode = document.getBoolean("moderation-staffMode");

        final Statistics statistics = user.statistics;
        if (document.containsKey("statistics-level")) statistics.level = document.getInteger("statistics-level");
        if (document.containsKey("statistics-experience")) statistics.experience = document.getInteger("statistics-experience");
        if (document.containsKey("statistics-totalExperience")) statistics.totalExperience = document.getInteger("statistics-totalExperience");
        if (document.containsKey("statistics-totalPlaytime")) statistics.totalPlaytime = document.getInteger("statistics-totalPlaytime");

        loadExtra(user);
        return user;
    }

    private User loadYML(final UUID uuid, final File file){
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        final User user = new User();
        user.uuid = uuid;

        final Player player = Bukkit.getPlayer(uuid);
        user.name = player != null ? player.getName() : configuration.getString("username");

        if (configuration.contains("group")) user.group = configuration.getString("group");

        final Moderation moderation = user.moderation;
        if (configuration.contains("moderation.staffMode")) moderation.staffMode = configuration.getBoolean("moderation.staffMode");

        final Statistics statistics = user.statistics;
        if (configuration.contains("statistics.level")) statistics.level = configuration.getInt("statistics.level");
        if (configuration.contains("statistics.experience")) statistics.experience = configuration.getInt("statistics.experience");
        if (configuration.contains("statistics.totalExperience")) statistics.totalExperience = configuration.getInt("statistics.totalExperience");
        if (configuration.contains("statistics.totalPlaytime")) statistics.totalPlaytime = configuration.getInt("statistics.totalPlaytime");

        loadExtra(user);
        file.delete();
        return user;
    }

    // Save user
    public void saveUser(final User user){
        final MongoHandler mongoHandler = agearynCore.getMongoHandler();
        if (!mongoHandler.connected()){
            saveYML(user, new File(agearynCore.getDataFolder(), "users/" + user.uuid + "/player.yml"));
            return;
        }

        saveMongo(user, mongoHandler);
    }

    private void saveMongo(final User user, final MongoHandler mongoHandler){
        if (!mongoHandler.connected()) {
            saveYML(user, new File(agearynCore.getDataFolder(), "users/" + user.uuid + "/player.yml"));
            return;
        }

        final MongoCollectionHandler collectionHandler = new MongoCollectionHandler().createCollection("Agearyn", "Users");
        final Document document = (collectionHandler.documentExist(user.uuid.toString()) ? collectionHandler.documentGet(user.uuid.toString()) :
                new Document("id", user.uuid.toString()));
        document.put("username", user.name);
        document.put("group", user.group);

        final Moderation moderation = user.moderation;
        document.put("moderation-staffMode", moderation.staffMode);

        final Statistics statistics = user.statistics;
        document.put("statistics-level", statistics.level);
        document.put("statistics-experience", statistics.experience);
        document.put("statistics-totalExperience", statistics.totalExperience);
        document.put("statistics-totalPlaytime", statistics.totalPlaytime);

        collectionHandler.documentUpdate(document);
        saveExtra(user);
    }

    private void saveYML(final User user, final File file){
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        configuration.set("id", user.uuid);
        configuration.set("username", user.name);
        configuration.set("group", user.group);

        final Moderation moderation = user.moderation;
        configuration.set("moderation.staffMode", moderation.staffMode);

        final Statistics statistics = user.statistics;
        configuration.set("statistics.level", statistics.level);
        configuration.set("statistics.experience", statistics.experience);
        configuration.set("statistics.totalExperience", statistics.totalExperience);
        configuration.set("statistics.totalPlaytime", statistics.totalPlaytime);

        saveExtra(user);
        try {
            configuration.save(file);
        }catch(IOException e){ e.printStackTrace(); }
    }

    // Extra
    private void loadExtra(final User user){
    }

    private void saveExtra(final User user){
    }
}
