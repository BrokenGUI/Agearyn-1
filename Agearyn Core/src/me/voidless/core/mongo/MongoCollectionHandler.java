package me.voidless.core.mongo;

import com.mongodb.client.MongoCollection;
import me.voidless.core.AgearynCore;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoCollectionHandler {

    public MongoCollection mongoCollection;

    public MongoCollectionHandler createCollection(final String database, final String collection){
        this.mongoCollection = AgearynCore.get().getMongoHandler().getDatabase(database).getCollection(collection);
        return this;
    }

    public MongoCollection getMongoCollection(){
        return mongoCollection;
    }

    public boolean documentExist(final String id){
        return documentGet(id) != null;
    }

    public void removeValue(final String id, final String value){
        final Bson remove = new Document("$unset", new Document(value, ""));
        this.mongoCollection.updateOne(documentGet(id), remove);
    }

    public void documentRemove(final String id){
        this.mongoCollection.deleteOne(new Document("id", id));
    }

    public void documentUpdate(final Document document){
        final String id = document.getString("id");
        if (this.documentExist(id)){
            final Bson update = new Document("$set", document);
            this.mongoCollection.updateOne(documentGet(id), update);
        } else this.mongoCollection.insertOne(document);
    }

    public Document documentGet(final String id){
        return (Document) this.mongoCollection.find(new Document("id", id)).first();
    }
}
