package me.delected.tcontrols;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftInventoryCustom;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DriverFile {

    public static ArrayList<Player> taskList = new ArrayList<Player>();

    public static void savePlayerInventory(Player p) throws FileNotFoundException {

        try {
            String base64 = inventoryToBase64(p.getInventory());
            File f = new File(Bukkit.getServer().getPluginManager().getPlugin("TControls").getDataFolder(), p.getUniqueId().toString() + ".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            writer.write(base64);

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ItemStack[] getSavedPlayerInventory(String data) {

        try {
            return inventoryFromBase64(data).getContents();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ItemStack[0];
    }

    public static String inventoryToBase64(Inventory inventory) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(inventory.getSize());

            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());

            //Converts the inventory and its contents to base64, This also saves item meta-data and inventory type
        } catch (Exception e) {
            throw new IllegalStateException("Could not convert inventory to base64.", e);
        }
    }

    public static Inventory inventoryFromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            CraftInventoryCustom inventory = new CraftInventoryCustom(null, dataInput.readInt());

            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }

            dataInput.close();
            return inventory;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new IOException("Could not decode inventory.", e);
        }
    }
    public static boolean isInDriverMode(Player p) {
        if (new File(Bukkit.getServer().getPluginManager().getPlugin("TControls").getDataFolder(), p.getUniqueId().toString() + ".txt").isFile()) {
            return true;
        }
        return false;
    }
    public static String readFile(File f, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(f.getPath()));
        return new String(encoded, encoding);
    }
}
