package ch.patrickjurt.itempicker.listeners;

import ch.patrickjurt.itempicker.ItemPicker;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftItemListener implements Listener {

    JavaPlugin plugin;

    public CraftItemListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        ItemStack itemStack = e.getCurrentItem();
        if (itemStack == null) return;
        ItemPicker.isCurrentItem(plugin, ItemPicker.formatItemName(itemStack));


        Entity player = e.getWhoClicked();
        player.sendMessage("You picked up: " + ItemPicker.formatItemName(itemStack));
    }
}
