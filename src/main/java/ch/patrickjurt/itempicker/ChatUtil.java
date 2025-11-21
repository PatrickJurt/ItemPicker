package ch.patrickjurt.itempicker;

import ch.patrickjurt.itempicker.filemanager.FileManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;

import static ch.patrickjurt.itempicker.ItemPicker.MAXITEMS;

public class ChatUtil {

    public static String createMessage(String message) {
        return ChatColor.RED + "[" +
                ChatColor.GOLD + "I" +
                ChatColor.YELLOW + "t" +
                ChatColor.GREEN + "e" +
                ChatColor.AQUA + "m" +
                ChatColor.BLUE + "P" +
                ChatColor.LIGHT_PURPLE + "icker" +
                ChatColor.RED + "] " +
                ChatColor.WHITE + " " + message;

    }

    public static String[] getProgressMessage(JavaPlugin plugin) {
        DecimalFormat df = new DecimalFormat("#.##");
        return new String[]{
                "Found items: " + ChatColor.GREEN + FileManager.countFoundItems(plugin) + "/" + MAXITEMS,
                "You have achieved " + ChatColor.GREEN + df.format((((double) FileManager.countFoundItems(plugin))/MAXITEMS)*100) + "%"};
    }

    public static void sendProgressMessage(JavaPlugin plugin) {
        String[] msgs = getProgressMessage(plugin);
        broadcast(plugin, msgs[0]);
        broadcast(plugin, msgs[1]);
    }

    public static void broadcast(JavaPlugin plugin, String message) {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            p.sendMessage(createMessage(message));
        }
    }
}
