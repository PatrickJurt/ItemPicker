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
        ItemStack itemStack = e.getItem().getItemStack();
        ItemPicker.isCurrentItem(plugin, ItemPicker.formatItemName(itemStack));
    }
}
