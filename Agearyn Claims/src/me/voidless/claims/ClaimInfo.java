package me.voidless.claims;

import java.util.ArrayList;
import java.util.UUID;

public class ClaimInfo {

    public UUID owner;
    public String mainClaim;
    public ArrayList<UUID> guests;

    public ClaimInfo(){
        this.owner = null;
        this.mainClaim = null;
        this.guests = new ArrayList<>();
    }

    public boolean canEdit(final UUID uuid){
        return uuid.equals(owner) || guests.contains(uuid);
    }
}
