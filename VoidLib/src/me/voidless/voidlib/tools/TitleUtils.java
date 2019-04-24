package me.voidless.voidlib.tools;

import me.voidless.voidlib.VoidLib;
import me.voidless.voidlib.tools.titles.Title;
import org.bukkit.entity.Player;

public class TitleUtils {

    public static void showTitle(final Player player, final Title title, final boolean override){
        VoidLib.get().getTitleManager().addTitle(player, title, override);
    }

    public static void showTitle(final Player player, final Title[] titles, final boolean override){
        for (final Title title : titles) {
            VoidLib.get().getTitleManager().addTitle(player, title, override);
        }
    }

    public static void stopLoop(final Player player){
        VoidLib.get().getTitleManager().stopLoop(player);
    }
}
