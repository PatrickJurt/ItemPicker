package ch.patrickjurt.itempicker;

import ch.patrickjurt.itempicker.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class Main extends JavaPlugin {

    public static BossBarManager bossBarManager;

    @Override
    public void onEnable() {
        getLogger().info("ItemPicker enabled!");

        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new PickupListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getServer().getPluginManager().registerEvents(new CraftItemListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        bossBarManager = new BossBarManager(this);

        ItemPicker.initialize(this);

    }

    @Override
    public void onDisable() {
        getLogger().info("ItemPicker disabled!");
    }
}
