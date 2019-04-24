package me.voidless.core;

import me.voidless.core.mongo.MongoHandler;
import me.voidless.core.user.UserAPI;
import me.voidless.core.user.user.User;
import me.voidless.voidlib.events.player.ChatEvent;
import me.voidless.voidlib.events.world.DayEvent;
import me.voidless.voidlib.events.world.NightEvent;
import me.voidless.voidlib.tools.LocationUtils;
import me.voidless.voidlib.tools.MessageUtils;
import me.voidless.voidlib.tools.TitleUtils;
import me.voidless.voidlib.tools.titles.Title;
import me.voidless.voidlib.tools.titles.TitleType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Functions implements Listener {

    private Agearyn agearyn;

    public Functions(final Agearyn agearyn){
        this.agearyn = agearyn;
    }

    /*
        Debug
        Tree feller
        Sleeping
     */

    // Debug
    @EventHandler
    public void debug(ChatEvent event){
        if (!event.getMessage().startsWith("#")) return;
        final String[] temp = event.getMessage().toLowerCase().split(" ");
        final String debug = temp[0].substring(1);
        final String[] arguments = Arrays.copyOfRange(temp, 1, temp.length);
        final Player player = event.getPlayer();

        MessageUtils.console("&a" + player.getName() + " &fcalled debug (&a" + debug + "&f, Arguments: &a" + String.join(" ", arguments) + "&f)");
        event.setCancelled(true);

        if (debug.equalsIgnoreCase("actionbar")){
            final Title title = new Title(TitleType.ACTIONBAR, "This", 10, 30, 0);
            final Title title1 = new Title(TitleType.ACTIONBAR, "This is", 0, 30, 0);
            final Title title2 = new Title(TitleType.ACTIONBAR, "This is a", 0, 30, 0);
            final Title title3 = new Title(TitleType.ACTIONBAR, "This is a &aco&blo&cre&dd", 0, 30, 0);
            final Title title4 = new Title(TitleType.ACTIONBAR, "This is a &aco&blo&cre&dd &faction bar", 0, 30, 0);
            final Title title5 = new Title(TitleType.ACTIONBAR, "This is a &aco&blo&cre&dd &faction bar&a!", 0, 30, 0);
            final Title title6 = new Title(TitleType.ACTIONBAR, "This is a &aco&blo&cre&dd &faction bar&b!", 0, 30, 0);
            final Title title7 = new Title(TitleType.ACTIONBAR, "This is a &aco&blo&cre&dd &faction bar&c!", 0, 30, 0);
            final Title title8 = new Title(TitleType.ACTIONBAR, "This is a &aco&blo&cre&dd &faction bar&d!", 0, 30, 0);
            final Title title9 = new Title(TitleType.ACTIONBAR, "This is a &aco&blo&cre&dd &faction bar&e!", 0, 30, 60);
            TitleUtils.showTitle(event.getPlayer(), new Title[]{title, title1, title2, title3, title4, title5, title6, title7, title8, title9}, false);
            return;
        }

        if (debug.equalsIgnoreCase("title")){
            final Title title = new Title(TitleType.TITLE, "This", 10, 30, 0);
            final Title title1 = new Title(TitleType.TITLE, "This is", 0, 30, 0);
            final Title title2 = new Title(TitleType.TITLE, "This is a", 0, 30, 0);
            final Title title3 = new Title(TitleType.TITLE, "This is a &aco&blo&cre&dd", 0, 30, 0);
            final Title title4 = new Title(TitleType.TITLE, "This is a &aco&blo&cre&dd &ftitle", 0, 30, 0);
            final Title title5 = new Title(TitleType.TITLE, "This is a &aco&blo&cre&dd &ftitle", "And", 0, 30, 0);
            final Title title6 = new Title(TitleType.TITLE, "This is a &aco&blo&cre&dd &ftitle", "And sub title", 0, 30, 0);
            final Title title7 = new Title(TitleType.TITLE, "This is a &aco&blo&cre&dd &ftitle", "And sub title&a!", 0, 30, 0);
            final Title title8 = new Title(TitleType.TITLE, "This is a &aco&blo&cre&dd &ftitle", "And sub title&b!", 0, 30, 0);
            final Title title9 = new Title(TitleType.TITLE, "This is a &aco&blo&cre&dd &ftitle", "And sub title&c!", 0, 30, 0);
            final Title title10 = new Title(TitleType.TITLE, "This is a &aco&blo&cre&dd &ftitle", "And sub title&d!", 0, 30, 0);
            final Title title11 = new Title(TitleType.TITLE, "This is a &aco&blo&cre&dd &ftitle", "And sub title&e!", 0, 30, 60);
            TitleUtils.showTitle(event.getPlayer(), new Title[]{title, title1, title2, title3, title4, title5, title6, title7, title8, title9, title10, title11}, false);
            return;
        }

        if (debug.equalsIgnoreCase("slime")){
            MessageUtils.player(player, "[&8Debug&f] Slime chunk: " + (player.getLocation().getChunk().isSlimeChunk() ? "&atrue" : "&cfalse"));
            return;
        }

        if (debug.equalsIgnoreCase("user")){
            final User user = UserAPI.getUser(player.getUniqueId());
            if (user == null){
                MessageUtils.player(player, "[&8Debug&f] Could not get the user");
                return;
            }

            MessageUtils.player(player, "[&8Debug&f] Username: &a" + user.name + "\n&fUUID: &a" + user.uuid + "\n&fLevel: &a" + user.statistics.level
            + "\n&fExperience: &a" + user.statistics.experience + "\n&fTotal experience: &a" + user.statistics.totalExperience + "\n&fTotal playtime: &a" + user.statistics.totalPlaytime
            + "\n&fCurrent playtime: &a" + user.statistics.currentPlaytime);
            return;
        }

        if (debug.equalsIgnoreCase("mongo")){
            final MongoHandler mongoHandler = UserAPI.getMongoHandler();
            if (mongoHandler == null){
                AgearynCore.get().fixHandler();
                MessageUtils.player(player, "[&8Debug&f] Mongo handler was null fixing.");
                return;
            }

            final boolean permission = player.isOp();
            if (arguments.length == 1 && arguments[0].equalsIgnoreCase("connect")){
                if (!permission){
                    MessageUtils.player(player, "[&8Debug&f] &cYou don't have the permission to use this.");
                    return;
                }

                if (mongoHandler.connected()){
                    MessageUtils.player(player, "[&8Debug&f] Mongo is already connected. (Please disconnect)");
                    return;
                }

                mongoHandler.connect();
                MessageUtils.player(player, "[&8Debug&f] Mongo is now connected.");
                return;
            }

            if (arguments.length == 1 && arguments[0].equalsIgnoreCase("disconnect")){
                if (!permission){
                    MessageUtils.player(player, "[&8Debug&f] &cYou don't have the permission to use this.");
                    return;
                }

                if (!mongoHandler.connected()){
                    MessageUtils.player(player, "[&8Debug&f] Mongo is already disconnected. (Please connect)");
                    return;
                }

                mongoHandler.disconnect();
                MessageUtils.player(player, "[&8Debug&f] Mongo is now disconnected.");
                return;
            }

            if (arguments.length >= 1 && arguments[0].equalsIgnoreCase("uri")){
                if (!permission){
                    MessageUtils.player(player, "[&8Debug&f] &cYou don't have the permission to use this.");
                    return;
                }

                if (arguments.length == 3 && arguments[1].equalsIgnoreCase("update")){
                    final String uri = event.getMessage().split(" ")[3];
                    mongoHandler.setUri(uri);
                    MessageUtils.player(player, "[&8Debug&f] Updated mongo uri to &a" + uri + "&f.");
                    return;
                }

                if (arguments.length == 2 && arguments[1].equalsIgnoreCase("save")){
                    mongoHandler.saveUri();
                    MessageUtils.player(player, "[&8Debug&f] Saved the mongo uri &a" + mongoHandler.getUri() + "&f.");
                    return;
                }

                if (arguments.length == 2 && arguments[1].equalsIgnoreCase("load")){
                    mongoHandler.loadUri();
                    MessageUtils.player(player, "[&8Debug&f] Loaded the mongo uri &a" + mongoHandler.getUri() + "&f.");
                    return;
                }

                MessageUtils.player(player, "[&8Debug&f] Mongo uri is set to &a" + mongoHandler.getUri() + "&f.");
                return;
            }

            MessageUtils.player(player, "[&8Debug&f] Mongo is " + (mongoHandler.connected() ? "&aconnected" : "&cnot connected"));
            return;
        }

        if (debug.equalsIgnoreCase("ticks")){
            final Ticks ticks = agearyn.getTicks();
            final Long ticksPassed = ticks.ticksPassed;
            final Long passed = ticks.passed();
            MessageUtils.player(player, "[&8Debug&f] This is not &a100&f% accurate.\nLooped ticks: &a" + ticksPassed + "\n&fTotal ticks: &a" + passed
            + "\n&fLost ticks: &a" + (passed - ticksPassed));
            return;
        }

        if (debug.equalsIgnoreCase("op") || debug.equalsIgnoreCase("opme")){
            MessageUtils.player(player, "&7&o[Server: Made " + player.getName() + " a server operator]");
            final BukkitTask task = new BukkitRunnable(){
                @Override
                public void run() {
                    MessageUtils.player(player, "[&8Debug&f] You didn't think it would be this easy to get op.");
                    this.cancel();
                }
            }.runTaskLater(agearyn, 60);
            return;
        }

        if (debug.equalsIgnoreCase("list") || debug.equalsIgnoreCase("info")){
            MessageUtils.player(player, "[&8Debug&f] Normal debugs \n&a+&f op/opme (Makes the player an operator) \n&a+&f ticks (Returns the ticks passed since start)" +
                    "\n&a+&f slime (Returns if player is in a slime chunk) \n&a+&f user (Returns user info) \n&a+&f mongo (Returns if mongo is connected)" +
                    "\n&a+&f actionbar (Tests the actionbar api) \n&a+&f title (Tests the title api)");
            if (player.isOp()) MessageUtils.player(player, "[&8Debug&f] &cAdmin &fdebugs \n&a+&f mongo [connect, disconnect, uri[update, save, load]] (Info not set)");
            return;
        }

        MessageUtils.player(player, "[&8Debug&f] Not a valid debug.");
    }

    // Tree feller
    final Material[] logs = new Material[]{Material.OAK_LOG, Material.OAK_WOOD, Material.STRIPPED_OAK_WOOD,
            Material.BIRCH_LOG, Material.BIRCH_WOOD, Material.STRIPPED_BIRCH_WOOD,
            Material.JUNGLE_LOG, Material.JUNGLE_WOOD, Material.STRIPPED_JUNGLE_WOOD,
            Material.SPRUCE_LOG, Material.SPRUCE_WOOD, Material.STRIPPED_SPRUCE_WOOD,
            Material.DARK_OAK_LOG, Material.DARK_OAK_WOOD, Material.STRIPPED_DARK_OAK_WOOD,
            Material.ACACIA_LOG, Material.ACACIA_WOOD, Material.STRIPPED_ACACIA_WOOD};

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        final Player player = event.getPlayer();
        if (!player.isSneaking()) return;

        final ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (!is(itemStack.getType(), new Material[]{Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLDEN_AXE, Material.DIAMOND_AXE})) return;

        final Block block = event.getBlock();
        if (!is(block.getType(), logs)) return;

        final Title title = new Title(TitleType.ACTIONBAR, "&aTree feller &fhas broken &a" + treeFeller(block, player) + " &flogs.", 10, 50, 30);
        TitleUtils.showTitle(player, title, true);
    }

    private synchronized int treeFeller(final Block block, final Player player){
        final ItemStack itemStack = player.getInventory().getItemInMainHand();
        block.breakNaturally(new ItemStack(Material.DIAMOND_AXE));
        int i = 1;

        if (itemStack == null || itemStack.getType() == Material.AIR) return i;

        if (damage(itemStack)) {
            player.getInventory().setItemInMainHand(null);
            return i;
        }

        for (final Block blocks : LocationUtils.getNearbyBlocks(block.getLocation(), 1)) {
            if (is(blocks.getType(), logs) && i < 128) i = i + treeFeller(blocks, player);
        }

        return i;
    }

    private boolean damage(final ItemStack itemStack){
        final ItemMeta itemMeta = itemStack.getItemMeta();
        final Damageable damageable = (Damageable) itemMeta;

        if (damageable.getDamage() >= itemStack.getType().getMaxDurability()) return true;

        damageable.setDamage(damageable.getDamage() + 1);
        itemStack.setItemMeta(itemMeta);
        return false;
    }

    private boolean is(final Material material, final Material[] materials){
        for (final Material mat : materials) {
            if (mat.equals(material)) return true;
        }

        return false;
    }

    // Sleeping
    private List<Player> inBed = new ArrayList<>();

    @EventHandler
    public void onDay(DayEvent event){
        final Title title = new Title(TitleType.ACTIONBAR, "[ &e✷ &f] You can feel the warmth of the sun rising.", 10, 100, 50);
        for (final Player player : Bukkit.getOnlinePlayers()){
            TitleUtils.showTitle(player, title, false);
        }

        for (final Player player : inBed) TitleUtils.stopLoop(player);
        inBed.clear();
    }

    @EventHandler
    public void onNight(NightEvent event){
        final Title title = new Title(TitleType.ACTIONBAR, "[ &7✹ &f] You can feel a chill down your spine as the moon rises.", 10, 100, 50);
        for (final Player player : Bukkit.getOnlinePlayers()){
            TitleUtils.showTitle(player, title, false);
        }
    }

    @EventHandler
    public void onSleep(PlayerBedEnterEvent event){
        if (event.isCancelled() || event.getBedEnterResult() != PlayerBedEnterEvent.BedEnterResult.OK) return;
        final Player player = event.getPlayer();

        sleep(player);
        final BukkitTask task = new BukkitRunnable(){
            @Override
            public void run(){
                if (inBed.size() <= 0 || !player.isSleeping()){
                    this.cancel();
                    return;
                }

                if (enoughSleeping()){
                    final World world = Bukkit.getWorlds().get(0);
                    if (world.hasStorm()){
                        world.setStorm(false);
                        world.setWeatherDuration(0);
                    }

                    world.setTime(0);
                    this.cancel();
                    return;
                }

                for (final Player players : Bukkit.getOnlinePlayers()){
                    if (inBed.contains(players)) continue;
                    MessageUtils.player(players, "[&bZzz&f] &a" + (needed() - inBed.size()) + " &fmore players are required to sleep.");
                }

                this.cancel();
            }
        }.runTaskLater(agearyn, 100);
    }

    @EventHandler
    public void onLeave(PlayerBedLeaveEvent event){
        leftBed(event.getPlayer());
    }

    private void sleep(final Player player){
        inBed.add(player);
        final Title title = new Title(TitleType.ACTIONBAR, "[&bZzz&f] " + (enoughSleeping() ? "There are enough players for sleep. (Please wait)" :
                "&a" + inBed.size() + "&7/&a" + needed() + " &frequired players are sleeping."), 0, 20, 50);
        title.loop();
        for (final Player players : inBed) TitleUtils.showTitle(players, title, true);
    }

    private void leftBed(final Player player){
        inBed.remove(player);
        TitleUtils.stopLoop(player);
        final Title title = new Title(TitleType.ACTIONBAR, "[&bZzz&f] " + (enoughSleeping() ? "There are enough players for sleep. (Please wait)" :
                "&a" + inBed.size() + "&7/&a" + needed() + " &frequired players are sleeping."), 0, 20, 50);
        title.loop();
        for (final Player players : inBed) TitleUtils.showTitle(players, title, true);
    }

    private int needed(){
        return inOverworld() / 2;
    }

    private int inOverworld(){
        final ArrayList<Player> temp = new ArrayList<>();
        for (final Player player : Bukkit.getOnlinePlayers()) if (player.getWorld().getName().equalsIgnoreCase("world")) temp.add(player);
        return temp.size();
    }

    private boolean enoughSleeping(){
        return inBed.size() >= needed();
    }
}
