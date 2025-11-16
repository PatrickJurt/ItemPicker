package ch.patrickjurt.itempicker;

import org.bukkit.ChatColor;

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
}
