package me.voidless.core.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import me.voidless.core.AgearynCore;
import me.voidless.voidlib.tools.MessageUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MongoHandler {

    private String uri;
    private MongoClient mongoClient;
    private AgearynCore agearynCore;

    public MongoHandler(){
        this.uri = null;
        this.mongoClient = null;
        this.agearynCore = null;
    }

    public MongoHandler connect(final AgearynCore agearynCore){
        this.agearynCore = agearynCore;
        if (agearynCore.getMongoHandler() != null && agearynCore.getMongoHandler().connected()) return this;
        if (uri == null) uri = loadUri();

        if (uri == null || !uri.toLowerCase().startsWith("mongodb")){
            MessageUtils.console("&f[&aMongoDB&f] Could not find a uri.");
            this.mongoClient = null;
            return this;
        }

        this.mongoClient = new MongoClient(new MongoClientURI(uri));
        MessageUtils.console("&f[&aMongoDB&f] Connection to mongo is complete. (URI: &a" + uri + "&f)");
        return this;
    }

    public MongoHandler connect(){
        if (agearynCore == null) return this;
        if (agearynCore.getMongoHandler().connected()) return this;
        if (uri == null) uri = loadUri();

        if (uri == null || !uri.toLowerCase().startsWith("mongodb")){
            MessageUtils.console("&f[&aMongoDB&f] Could not find a uri.");
            this.mongoClient = null;
            return this;
        }

        this.mongoClient = new MongoClient(new MongoClientURI(uri));
        MessageUtils.console("&f[&aMongoDB&f] Connection to mongo is complete. (URI: &a" + uri + "&f)");
        return this;
    }

    public void disconnect(){
        this.mongoClient = null;
    }

    public MongoClient getClient() {
        return this.mongoClient;
    }

    public MongoDatabase getDatabase(final String database) {
        return this.mongoClient.getDatabase(database);
    }

    public boolean connected(){
        return mongoClient != null;
    }

    public String getUri(){
        return uri;
    }

    public void setUri(final String uri){
        this.uri = uri;
    }

    public String loadUri(){
        final File file = new File(agearynCore.getDataFolder(), "mongo.yml");
        if (!file.exists()) return null;

        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        final String uri = configuration.getString("mongo_uri");
        this.uri = uri;
        return uri;
    }

    public void saveUri(){
        if (uri == null) return;
        final File file = new File(agearynCore.getDataFolder(), "mongo.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        configuration.set("mongo_uri", uri);

        try {
            configuration.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
