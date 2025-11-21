package ch.patrickjurt.itempicker.listeners;

import ch.patrickjurt.itempicker.ChatUtil;
import ch.patrickjurt.itempicker.Main;
import ch.patrickjurt.itempicker.filemanager.FileManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import static ch.patrickjurt.itempicker.ItemPicker.MAXITEMS;

public class PlayerJoinListener implements Listener {

    JavaPlugin plugin;

    public PlayerJoinListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        Main.bossBarManager.addPlayer(p);
        p.sendMessage(ChatUtil.createMessage("Good to see you, " + p.getName() + "!"));
        p.sendMessage(ChatUtil.createMessage(ChatColor.GREEN + "" + FileManager.countFoundItems(plugin) + "/" + MAXITEMS));
        p.sendMessage(ChatUtil.createMessage("You have achieved " + (0.0 + FileManager.countFoundItems(plugin)/MAXITEMS*100) + "%"));
    }
}
