package ch.patrickjurt.itempicker.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class PickupListener implements Listener {

    public PickupListener(JavaPlugin plugin) {
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void onPickup(EntityPickupItemEvent e) {
        Player p = e.getEntity().getKiller();
        if (p != null){
            String itemName = e.getItem().getItemStack().getType().name();
            String[] specialItemCategories = {"POTION", "ENCHANTED_BOOK", "GOAT_HORN", "TIPPED_ARROW"};
            if (Arrays.asList(specialItemCategories).contains(itemName)) {
                ItemMeta itemMeta = e.getItem().getItemStack().getItemMeta();
                if (itemMeta != null) {
                    itemName = itemMeta.toString();
                }
            }
            p.sendMessage("You picked up " + itemName);
        }
    }
}
