package me.voidless.core;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class Recipes {

    private Agearyn agearyn;

    public Recipes(final Agearyn agearyn){
        this.agearyn = agearyn;

        oakLog();
        birchLog();
        spruceLog();
        jungleLog();
        darkOakLog();
        acaciaLog();

        slime();
        saddle();
        saddle1();
        ironArmor();
        goldArmor();
        diamondArmor();
        nametag();
        nametag1();
    }

    /*
        Bark to log,
        Slime,
        Saddle,
        Saddle1,
        Iron horse armor,
        Gold horse armor,
        Diamond horse armor,
        Nametag,
        Nametag1
     */

     // Bark to log
    private void oakLog(){
        final NamespacedKey key = new NamespacedKey(agearyn, "oak_log");
        final ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.OAK_LOG, 4));

        recipe.addIngredient(3, Material.OAK_WOOD);
        Bukkit.addRecipe(recipe);
    }

    private void birchLog(){
        final NamespacedKey key = new NamespacedKey(agearyn, "birch_log");
        final ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.BIRCH_LOG, 4));

        recipe.addIngredient(3, Material.BIRCH_WOOD);
        Bukkit.addRecipe(recipe);
    }

    private void spruceLog(){
        final NamespacedKey key = new NamespacedKey(agearyn, "spruce_log");
        final ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.SPRUCE_LOG, 4));

        recipe.addIngredient(3, Material.SPRUCE_WOOD);
        Bukkit.addRecipe(recipe);
    }

    private void jungleLog(){
        final NamespacedKey key = new NamespacedKey(agearyn, "jungle_log");
        final ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.JUNGLE_LOG, 4));

        recipe.addIngredient(3, Material.JUNGLE_WOOD);
        Bukkit.addRecipe(recipe);
    }

    private void darkOakLog(){
        final NamespacedKey key = new NamespacedKey(agearyn, "dark_oak_log");
        final ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.DARK_OAK_LOG, 4));

        recipe.addIngredient(3, Material.DARK_OAK_WOOD);
        Bukkit.addRecipe(recipe);
    }

    private void acaciaLog(){
        final NamespacedKey key = new NamespacedKey(agearyn, "ACACIA_log");
        final ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.ACACIA_LOG, 4));

        recipe.addIngredient(3, Material.ACACIA_WOOD);
        Bukkit.addRecipe(recipe);
    }

    // Slime
    private void slime(){
        final NamespacedKey key = new NamespacedKey(agearyn, "slime_ball");
        final ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.SLIME_BALL));

        recipe.addIngredient(2, Material.MOSSY_COBBLESTONE);
        Bukkit.addRecipe(recipe);
    }

    // Saddle
    private void saddle(){
        final NamespacedKey key = new NamespacedKey(agearyn, "saddle");
        final ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.SADDLE));

        recipe.shape("LLL", "I I", "   ");
        recipe.setIngredient('L', Material.LEATHER);
        recipe.setIngredient('I', Material.IRON_INGOT);

        Bukkit.addRecipe(recipe);
    }

    // Saddle1
    private void saddle1(){
        final NamespacedKey key = new NamespacedKey(agearyn, "saddle1");
        final ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.SADDLE));

        recipe.shape("   ", "LLL", "I I");
        recipe.setIngredient('L', Material.LEATHER);
        recipe.setIngredient('I', Material.IRON_INGOT);

        Bukkit.addRecipe(recipe);
    }

    // Iron horse armor
    private void ironArmor(){
        final NamespacedKey key = new NamespacedKey(agearyn, "iron_horse_armor");
        final ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.IRON_HORSE_ARMOR));

        recipe.shape("I  ", "III", "ISI");
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('S', Material.SADDLE);

        Bukkit.addRecipe(recipe);
    }

    // Gold horse armor
    private void goldArmor(){
        final NamespacedKey key = new NamespacedKey(agearyn, "gold_horse_armor");
        final ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.GOLDEN_HORSE_ARMOR));

        recipe.shape("G  ", "GGG", "GSG");
        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('S', Material.SADDLE);

        Bukkit.addRecipe(recipe);
    }

    // Diamond horse armor
    private void diamondArmor(){
        final NamespacedKey key = new NamespacedKey(agearyn, "diamond_horse_armor");
        final ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.DIAMOND_HORSE_ARMOR));

        recipe.shape("D  ", "DDD", "DSD");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('S', Material.SADDLE);

        Bukkit.addRecipe(recipe);
    }

    // Nametag
    private void nametag(){
        final NamespacedKey key = new NamespacedKey(agearyn, "nametag");
        final ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.NAME_TAG));

        recipe.shape("S  ", " P ", "  P");
        recipe.setIngredient('S', Material.STRING);
        recipe.setIngredient('P', Material.PAPER);

        Bukkit.addRecipe(recipe);
    }

    // Nametag1
    private void nametag1(){
        final NamespacedKey key = new NamespacedKey(agearyn, "nametag1");
        final ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.NAME_TAG));

        recipe.shape("  S", " P ", "P  ");
        recipe.setIngredient('S', Material.STRING);
        recipe.setIngredient('P', Material.PAPER);

        Bukkit.addRecipe(recipe);
    }
}
