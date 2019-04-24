package me.voidless.claims;

import org.bukkit.Chunk;

public class Claim {

    public ClaimInfo claimInfo;
    public boolean edited;
    public final int x;
    public final int z;

    public Claim(final Chunk chunk){
        this.claimInfo = new ClaimInfo();
        this.edited = false;
        this.x = chunk.getX();
        this.z = chunk.getZ();
    }

    public Claim(final int x, final int z){
        this.claimInfo = new ClaimInfo();
        this.edited = false;
        this.x = x;
        this.z = z;
    }
}
