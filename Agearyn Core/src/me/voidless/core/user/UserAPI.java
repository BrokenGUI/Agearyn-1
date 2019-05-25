package me.voidless.core.user;

import me.voidless.core.AgearynCore;
import me.voidless.core.groups.Group;
import me.voidless.core.mongo.MongoHandler;
import me.voidless.core.user.user.User;

import java.util.UUID;

public class UserAPI {

    private static AgearynCore getPlugin(){
        return AgearynCore.get();
    }

    public static MongoHandler getMongoHandler() {
        return getPlugin().getMongoHandler();
    }

    public static User getUser(final UUID uuid){
        return getPlugin().getUserManager().getUser(uuid);
    }

    public static Group getGroup(final User user) {
        return getPlugin().getGroupManager().getGroup(user.group);
    }
}
