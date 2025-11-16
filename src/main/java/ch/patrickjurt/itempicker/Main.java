package ch.patrickjurt.itempicker;

import ch.patrickjurt.itempicker.listeners.PlayerDeathListener;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class Main extends JavaPlugin {


    @Override
    public void onEnable() {
        getLogger().info("ItemPicker enabled!");

        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("ItemPicker disabled!");
    }
}
