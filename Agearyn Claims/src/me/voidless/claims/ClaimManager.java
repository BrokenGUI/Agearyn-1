package me.voidless.claims;

import org.bukkit.Chunk;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class ClaimManager {

    private Claims claims;
    public HashMap<String, Claim> claimMap;

    public ClaimManager(final Claims claims){
        this.claims = claims;
        this.claimMap = new HashMap<>();
    }

    public Claim getClaim(final int x, final int z){
        final String key = getKey(x, z);
        if (claimMap.containsKey(key)) return claimMap.get(key);
        return loadClaim(new Claim(x, z), key);
    }

    public Claim getClaim(final Chunk chunk){
        return getClaim(chunk.getX(), chunk.getZ());
    }

    public Claim getClaim(final String id){
        final String[] coords = id.split(":");
        return getClaim(Integer.valueOf(coords[0]), Integer.valueOf(coords[1]));
    }

    private Claim loadClaim(final Claim claim, final String id){
        final File file = new File(claims.getDataFolder(), "claims/" + id + ".yml");
        if (!file.exists()) return claim;

        claim.edited = true;
        final ClaimInfo claimInfo = claim.claimInfo;
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        if (configuration.contains("owner")) claimInfo.owner = UUID.fromString(configuration.getString("owner"));
        if (configuration.contains("mainClaim")) claimInfo.mainClaim = configuration.getString("mainClaim");
        if (configuration.contains("guests")) claimInfo.guests = loadGuests(configuration);
        return claim;
    }

    public void saveClaim(final Claim claim){
        if (!claim.edited) return;
        final File file = new File(claims.getDataFolder(), "claims/" + getClaim(claim.x, claim.z) + ".yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        final ClaimInfo claimInfo = claim.claimInfo;
        configuration.set("owner", claimInfo.owner.toString());
        configuration.set("mainClaim", claimInfo.mainClaim);
        // Credits Vitalii Fedorenko | https://stackoverflow.com/questions/599161/best-way-to-convert-an-arraylist-to-a-string
        configuration.set("guests", claimInfo.guests.stream().map(Objects::toString).collect(Collectors.joining(":")));

        try{
            configuration.save(file);
        }catch(IOException e){ e.printStackTrace(); }
    }

    private ArrayList<UUID> loadGuests(final YamlConfiguration configuration){
        final ArrayList<UUID> guests = new ArrayList<>();
        for (final String uuid : configuration.getString("guests").split(":")){
            guests.add(UUID.fromString(uuid));
        }

        return guests;
    }

    private String getKey(final int x, final int z){
        return x + ":" + z;
    }
}
