package ch.patrickjurt.itempicker;

import ch.patrickjurt.itempicker.filemanager.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class ItemPicker {

    // 🔁 Reusable file name constants
    private static final String CURRENT_ITEM_FILE = "currentItem.txt";
    private static final String FOUND_ITEMS_FILE = "foundItems.txt";
    private static final String REMAINING_ITEMS_FILE = "remainingItems.txt";
    private static final String ALL_ITEMS_RESOURCE = "allItems";
    public static final int MAXITEMS = 1627;

    public static String currentItem;

    public static void initialize(JavaPlugin plugin) {
        FileManager.existFile(plugin, CURRENT_ITEM_FILE);
        FileManager.existFile(plugin, FOUND_ITEMS_FILE);
        FileManager.existFile(plugin, REMAINING_ITEMS_FILE);

        if (FileManager.isEmpty(plugin, CURRENT_ITEM_FILE)) {
            if (FileManager.isEmpty(plugin, FOUND_ITEMS_FILE)) {
                fillRemainingItems(plugin);
            }
            getNewCurrentItem(plugin);
        }
        saveCurrentItem(plugin);
    }

    public static void isCurrentItem(JavaPlugin plugin, String itemName){
        if (itemName.equalsIgnoreCase(currentItem)){
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            }
            getNext(plugin);
        }
    }

    public static void getNext(JavaPlugin plugin){
        getNewCurrentItem(plugin);
        ChatUtil.sendProgressMessage(plugin);
    }

    private static void saveCurrentItem(JavaPlugin plugin) {
        FileManager.getCurrentItem(plugin);
        currentItem = FileManager.getCurrentItem(plugin);
        Main.bossBarManager.updateMessage(currentItem.replace('_', ' '));
    }

    public static void fillRemainingItems(JavaPlugin plugin) {
        FileManager.copyFromResourcesToPluginFolder(plugin, ALL_ITEMS_RESOURCE, REMAINING_ITEMS_FILE);
    }

    public static void getNewCurrentItem(JavaPlugin plugin) {
        File currentFile = getFile(plugin, CURRENT_ITEM_FILE);
        File foundFile = getFile(plugin, FOUND_ITEMS_FILE);
        File remainingFile = getFile(plugin, REMAINING_ITEMS_FILE);

        if (!FileManager.isEmpty(plugin, CURRENT_ITEM_FILE)) {
            putCurrentItemToFound(plugin, currentFile, foundFile);
        }
        copyFirstRemainingToCurrent(plugin, remainingFile, currentFile);
        Main.bossBarManager.updateMessage(currentItem.replace('_', ' '));
        removeFirstRemaining(plugin, remainingFile);
    }

    public static void putCurrentItemToFound(JavaPlugin plugin, File currentFile, File foundFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(foundFile, true))) {
            writer.write(currentItem);
            writer.newLine();
            plugin.getLogger().info("Appended current item to " + FOUND_ITEMS_FILE + ": " + currentItem);

        } catch (IOException e) {
            plugin.getLogger().severe("Error appending current item to " + FOUND_ITEMS_FILE);
            e.printStackTrace();
        }
    }

    public static void copyFirstRemainingToCurrent(JavaPlugin plugin,  File remainingFile, File currentFile) {
        try (
                BufferedReader reader = new BufferedReader(new FileReader(remainingFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))
        ) {
            String newCurrentItem = reader.readLine();
            if (newCurrentItem != null && !newCurrentItem.trim().isEmpty()) {
                writer.write(newCurrentItem);
                writer.newLine();
                currentItem = newCurrentItem;
                plugin.getLogger().info("Copied first item to " + CURRENT_ITEM_FILE + ": " + newCurrentItem);
            } else {
                plugin.getLogger().warning(REMAINING_ITEMS_FILE + " is empty!");
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Error copying first item to " + CURRENT_ITEM_FILE);
            e.printStackTrace();
        }
    }

    public static void removeFirstRemaining(JavaPlugin plugin, File remainingFile) {
        File tempFile = getFile(plugin, "remainingItems_temp.txt");
        try (
                BufferedReader reader = new BufferedReader(new FileReader(remainingFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line = reader.readLine(); // skip first
            if (line == null) {
                plugin.getLogger().warning(REMAINING_ITEMS_FILE + " was already empty.");
                return;
            }

            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            plugin.getLogger().severe("Error removing first line from " + REMAINING_ITEMS_FILE);
            e.printStackTrace();
            return;
        }

        if (!remainingFile.delete()) {
            plugin.getLogger().severe("Failed to delete original " + REMAINING_ITEMS_FILE);
            return;
        }

        if (!tempFile.renameTo(remainingFile)) {
            plugin.getLogger().severe("Failed to rename temp file to " + REMAINING_ITEMS_FILE);
        } else {
            plugin.getLogger().info("Removed first item from " + REMAINING_ITEMS_FILE);
        }
    }

    // 🔧 Helper to get file from plugin folder
    private static File getFile(JavaPlugin plugin, String filename) {
        return new File(plugin.getDataFolder(), filename);
    }

    public static String formatItemName(ItemStack itemStack) {
        String itemName = itemStack.getType().name();
        String name;

        switch (itemName) {
            case "POTION", "SPLASH_POTION", "LINGERING_POTION", "TIPPED_ARROW" -> name = itemName + "_" + getSpecialItem(itemStack, false);
            case "ENCHANTED_BOOK" -> name = itemName + "_" + getSpecialItem(itemStack, true);
            case "GOAT_HORN" -> name = getSpecialItem(itemStack, false);
            default -> name = itemName;
        }
        return name;
    }

    public static String getSpecialItem(ItemStack itemStack, boolean isBook){
        ItemMeta itemMeta = itemStack.getItemMeta();
        String marker = "minecraft:";
        String metaAsString = itemMeta.toString();
        int index = metaAsString.indexOf(marker);
        if (index != -1) {
            if (isBook) {
                return metaAsString.substring(index + marker.length(), metaAsString.length() - 2).toUpperCase();
            }else{
                return metaAsString.substring(index + marker.length(), metaAsString.length() - 1).toUpperCase();
            }
        }
        return "[ERROR] Item has weird meta: " + itemMeta;
    }
}
