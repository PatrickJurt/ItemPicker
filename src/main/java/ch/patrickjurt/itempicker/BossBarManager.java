package ch.patrickjurt.itempicker;

import ch.patrickjurt.itempicker.filemanager.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BossBarManager {

    private final BossBar bossBar;

    public BossBarManager(JavaPlugin plugin) {
        // Create the BossBar
        bossBar = Bukkit.createBossBar("", BarColor.GREEN, BarStyle.SOLID);
        bossBar.setProgress(FileManager.countFoundItems(plugin) / ItemPicker.MAXITEMS);

        // Add all online players
        for (Player player : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(player);
        }

        // Optional: make it visible immediately
        bossBar.setVisible(true);
    }

    // Optional: call this when a new player joins
    public void addPlayer(Player player) {
        bossBar.addPlayer(player);
    }

    // Optional: update the message
    public void updateMessage(String message) {
        bossBar.setTitle(message);
    }

    // Optional: hide the bar
    public void hide() {
        bossBar.setVisible(false);
    }
}

