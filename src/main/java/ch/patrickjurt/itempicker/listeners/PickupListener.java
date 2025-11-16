package ch.patrickjurt.itempicker.listeners;

import ch.patrickjurt.itempicker.ItemPicker;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class PickupListener implements Listener {

    JavaPlugin plugin;

    public PickupListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void onPickup(EntityPickupItemEvent e) {
        Entity player = e.getEntity();
        ItemStack itemStack = e.getItem().getItemStack();
        String itemName = itemStack.getType().name();
        String name;

        switch (itemName) {
            case "POTION", "SPLASH_POTION", "LINGERING_POTION", "TIPPED_ARROW" -> name = itemName + "_" + getSpecialItem(itemStack, false);
            case "ENCHANTED_BOOK" -> name = itemName + "_" + getSpecialItem(itemStack, true);
            case "GOAT_HORN" -> name = getSpecialItem(itemStack, false);
            default -> name = itemName;
        }
        player.sendMessage("You picked up " + name);
        ItemPicker.isCurrentItem(plugin, name);
    }

    public String getSpecialItem(ItemStack itemStack, boolean isBook){
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
