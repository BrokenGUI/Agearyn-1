package me.voidless.claims;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ClaimHandler implements Listener {

    private Claims claims;

    public ClaimHandler(final Claims claims){
        this.claims = claims;
    }

    @EventHandler
    public void onLoad(ChunkLoadEvent event){
        claims.getClaimManager().getClaim(event.getChunk());
    }

    @EventHandler
    public void onUnload(ChunkUnloadEvent event){
        final Claim claim = claims.getClaimManager().getClaim(event.getChunk());
        if (claim.edited) claims.getClaimManager().saveClaim(claim);
        claims.getClaimManager().claimMap.remove(claim.x + ":" + claim.z);
    }
}
