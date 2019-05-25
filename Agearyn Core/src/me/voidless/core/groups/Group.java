package me.voidless.core.groups;

public class Group {

    public String id;
    public String name;
    public int permission;
    public int donorPermission;

    public Group(final String id){
        this.id = id;
        this.name = id;
        this.permission = 0;
        this.donorPermission = 0;
    }
}
