package ch.patrickjurt.itempicker;

import ch.patrickjurt.itempicker.listeners.CraftItemListener;
import ch.patrickjurt.itempicker.listeners.InventoryClickListener;
import ch.patrickjurt.itempicker.listeners.PickupListener;
import ch.patrickjurt.itempicker.listeners.PlayerDeathListener;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class Main extends JavaPlugin {


    @Override
    public void onEnable() {
        getLogger().info("ItemPicker enabled!");

        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new PickupListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getServer().getPluginManager().registerEvents(new CraftItemListener(this), this);

        ItemPicker.initialize(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("ItemPicker disabled!");
    }
}
