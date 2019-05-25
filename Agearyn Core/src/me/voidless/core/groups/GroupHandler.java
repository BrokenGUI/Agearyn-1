package me.voidless.core.groups;

import me.voidless.core.AgearynCore;
import me.voidless.core.mongo.MongoHandler;

public class GroupHandler {

    private AgearynCore agearynCore;
    private MongoHandler mongoHandler;

    public GroupHandler(final AgearynCore agearynCore, final MongoHandler mongoHandler){
        this.agearynCore = agearynCore;
        this.mongoHandler = mongoHandler;
    }

    public Group loadGroup(final String groupid){

    }

    public void updateGroup(final Group group){

    }
}
