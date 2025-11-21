package ch.patrickjurt.itempicker.filemanager;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileManager {

    public static void existFile(JavaPlugin plugin, String filename) {
        File file = new File(plugin.getDataFolder(), filename);

        // Ensure plugin folder exists
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        // If file exists, return true
        if (file.exists()) {
            plugin.getLogger().info("[ItemPicker] File found: " + filename);
            return;
        } else{
            plugin.getLogger().warning(filename + " does not exist!");
        }

        // Try to create the file
        try {
            boolean created = file.createNewFile();
            if (created) {
                plugin.getLogger().info("[ItemPicker] Created missing file: " + filename);
            }
        } catch (IOException e) {
            plugin.getLogger().severe("[ItemPicker] Failed to create file: " + filename);
            e.printStackTrace();
        }
    }

    public static boolean isEmpty(JavaPlugin plugin, String filename) {
        File file = new File(plugin.getDataFolder(), filename);

        // Check if file exists and is empty
        return file.exists() && file.length() == 0;
    }

    public static void copyFromResourcesToPluginFolder(JavaPlugin plugin, String fileFrom, String fileTo) {
        try (InputStream inputStream = plugin.getResource(fileFrom)) {
            if (inputStream == null) {
                plugin.getLogger().severe(fileFrom + " not found in resources!");
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            List<String> lines = new ArrayList<>();

            // Read all lines into a list
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            // Shuffle the list
            Collections.shuffle(lines);

            // Write shuffled lines to fileTo
            File outputFile = new File(plugin.getDataFolder(), fileTo);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                for (String item : lines) {
                    writer.write(item);
                    writer.newLine();
                }
                plugin.getLogger().info("Copied and randomized " + fileFrom + " to " + fileTo);
            }

        } catch (IOException e) {
            plugin.getLogger().severe("Error copying " + fileFrom + " to " + fileTo);
            e.printStackTrace();
        }
    }

    public static String getCurrentItem(JavaPlugin plugin) {
        try {
            return Files.readString(new File(plugin.getDataFolder(), "currentItem.txt").toPath()).trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int countFoundItems(JavaPlugin plugin) {
        File file = new File(plugin.getDataFolder(), "foundItems.txt");
        if (!file.exists()) {
            return 0; // File doesn't exist, so no items found
        }

        try {
            List<String> lines = Files.readAllLines(file.toPath());
            return lines.size();
        } catch (IOException e) {
            e.printStackTrace();
            return -1; // Indicates an error occurred
        }

    }
}
