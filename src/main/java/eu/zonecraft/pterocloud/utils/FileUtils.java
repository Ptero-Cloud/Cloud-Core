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
        if(!SERVICE_FOLDER.exists()) {
            SERVICE_FOLDER.mkdirs();
        }
        if(!MODULES_FOLDER.exists()) {
            MODULES_FOLDER.mkdirs();
        }
        if (!CONFIG_FILE.exists()) {
            CONFIG_FILE.getParentFile().mkdirs();
            try {
                CONFIG_FILE.createNewFile();
                Storage storage = new Storage(CONFIG_FILE);
                storage.setArray("pterodactylConfiguration", "host", "https://pterodactyl.example1.com");
                storage.setArray("pterodactylConfiguration", "clientAPI", "Client-API-Key");
                storage.setArray("pterodactylConfiguration", "applicationAPI", "Application-API-Key");
                storage.setArray("pterodactylConfiguration", "ownerID", "1");
                storage.setArray("cloudConfiguration", "cloudPort", "8080");
                storage.setArray("cloudConfiguration", "proxyStartPort", "25565");
                storage.setArray("cloudConfiguration", "minecraftStartPort", "25566");
                storage.setArray("cloudConfiguration", "checkForUpdates", "true");
                storage.setArray("cloudConfiguration", "isSetup", "false");
            } catch (Exception e) {
                MessageUtils.sendMessage(MessageType.ERROR, "An error occurred while creating the Config file.");
                MessageUtils.sendMessage(MessageType.ERROR, "Error: " + e.getMessage());
            }
        }
    }
}
