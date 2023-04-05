package eu.zonecraft.pterocloud.utils;

import eu.zonecraft.pterocloud.utils.message.MessageType;
import eu.zonecraft.pterocloud.utils.message.MessageUtils;
import eu.zonecraft.pterocloud.utils.storage.Storage;

import java.io.File;

public class FileUtils {

    public static File CONFIG_FILE = new File("storage/config.json");
    public static File MODULES_FOLDER = new File("modules/");
    public static File GROUPS_FOLDER = new File("groups/");
    public static File LOG_FOLDER = new File("storage/logs/");
    public static File LOG_FILE = new File("storage/logs/latest.log");
    public static File SERVICE_FOLDER = new File("service/");

    public static void createFiles() {
        if (!SERVICE_FOLDER.exists()) {
            SERVICE_FOLDER.mkdirs();
        }
        if (!MODULES_FOLDER.exists()) {
            MODULES_FOLDER.mkdirs();
        }
        if (!CONFIG_FILE.exists()) {
            CONFIG_FILE.getParentFile().mkdirs();
            try {
                CONFIG_FILE.createNewFile();
                Storage storage = new Storage(CONFIG_FILE);
                storage.setArray("pterodactylConfiguration", "host", "https://pterodactyl.example1.com");
                storage.setArray("pterodactylConfiguration", "clientKey", "Client-API-Key");
                storage.setArray("pterodactylConfiguration", "applicationKey", "Application-API-Key");
                storage.setArray("pterodactylConfiguration", "ownerID", "1");
                storage.setArray("cloudConfiguration", "cloudPort", "8080");
                storage.setArray("cloudConfiguration", "proxyStartPort", "25565");
                storage.setArray("cloudConfiguration", "minecraftStartPort", "25566");
                storage.setArray("cloudConfiguration", "checkForUpdates", "true");
                storage.setArray("cloudConfiguration", "isSetup", "false");
                storage.setArray("cloudAPIConfiguration", "port", "3030");
                storage.setArray("cloudAPIConfiguration", "apiKey", getRandomAPIKey());
            } catch (Exception e) {
                MessageUtils.sendMessage(MessageType.ERROR, "An error occurred while creating the Config file.");
                MessageUtils.sendMessage(MessageType.ERROR, "Error: " + e.getMessage());
            }
        }
    }

    private static String getRandomAPIKey() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder pass = new StringBuilder();
        for (int x = 0; x < 8; x++) {
            int i = (int) Math.floor(Math.random() * 62);
            pass.append(chars.charAt(i));
        }
        return pass.toString();
    }
}
