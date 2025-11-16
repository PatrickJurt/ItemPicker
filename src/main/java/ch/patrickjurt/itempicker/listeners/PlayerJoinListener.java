package ch.patrickjurt.itempicker.listeners;

import ch.patrickjurt.itempicker.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Main.bossBarManager.addPlayer(event.getPlayer());
    }
}
