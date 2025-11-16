package ch.patrickjurt.itempicker;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("ItemPicker enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ItemPicker disabled!");
    }
}
