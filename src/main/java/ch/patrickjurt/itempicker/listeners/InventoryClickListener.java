package ch.patrickjurt.itempicker.listeners;

import ch.patrickjurt.itempicker.ItemPicker;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryClickListener implements Listener {

    JavaPlugin plugin;

    public InventoryClickListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        ItemStack itemStack = e.getCurrentItem();
        if (itemStack == null) return;
        ItemPicker.isCurrentItem(plugin, ItemPicker.formatItemName(itemStack));
    }
}
