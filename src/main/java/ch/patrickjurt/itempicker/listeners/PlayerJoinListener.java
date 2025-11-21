package ch.patrickjurt.itempicker.listeners;

import ch.patrickjurt.itempicker.ChatUtil;
import ch.patrickjurt.itempicker.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

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
        p.sendMessage(ChatUtil.createMessage(ChatUtil.getProgressMessage(plugin)[0]));
        p.sendMessage(ChatUtil.createMessage(ChatUtil.getProgressMessage(plugin)[1]));
    }
}
