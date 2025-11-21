package ch.patrickjurt.itempicker.commands;

import ch.patrickjurt.itempicker.ChatUtil;
import ch.patrickjurt.itempicker.ItemPicker;
import ch.patrickjurt.itempicker.filemanager.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import static ch.patrickjurt.itempicker.ItemPicker.MAXITEMS;
import static ch.patrickjurt.itempicker.ItemPicker.getNewCurrentItem;

public class ItemPickerCommand implements CommandExecutor {

    JavaPlugin plugin;

    public ItemPickerCommand(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(ChatUtil.createMessage("ItemPicker Usage:"));
            player.sendMessage(ChatUtil.createMessage("/itempicker skip - Puts current item to found"));
            return true;
        }

        if (args[0].equalsIgnoreCase("skip")) {
            handleSkip();
            return true;
        }
        return true;
    }

    private void handleSkip() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.BLOCK_DECORATED_POT_SHATTER, 1.0f, 1.0f);
            player.sendMessage(ChatUtil.createMessage("Item skipped!"));
        }
        ItemPicker.getNext(plugin);
    }
}
