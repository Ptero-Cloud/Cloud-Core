package eu.zonecraft.pterocloud;

import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import eu.zonecraft.pterocloud.commands.CommandManager;
import eu.zonecraft.pterocloud.groups.GroupManager;
import eu.zonecraft.pterocloud.manager.PteroAuthManager;
import eu.zonecraft.pterocloud.manager.VersionManager;
import eu.zonecraft.pterocloud.screens.ScreenManager;
import eu.zonecraft.pterocloud.service.ServiceManager;
import eu.zonecraft.pterocloud.service.ServiceQueue;
import eu.zonecraft.pterocloud.utils.FileUtils;
import eu.zonecraft.pterocloud.utils.Utils;
import eu.zonecraft.pterocloud.utils.message.Color;
import eu.zonecraft.pterocloud.utils.message.MessageType;
import eu.zonecraft.pterocloud.utils.message.MessageUtils;
import eu.zonecraft.pterocloud.module.ModuleManager;
import eu.zonecraft.pterocloud.utils.storage.Storage;
import lombok.Getter;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.IOException;

public class PteroCloud {

    @Getter
    private static PteroCloud instance;

    @Getter
    private static Utils utils;

    @Getter
    private static CommandManager commandManager;

    @Getter
    private static ModuleManager moduleManager;

    @Getter
    public PteroClient pteroClient;

    @Getter
    public PteroApplication pteroApplication;

    @Getter
    public GroupManager groupManager;

    @Getter
    public VersionManager versionManager;

    @Getter
    public ScreenManager screenManager;

    @Getter
    public ServiceManager serviceManager;

    @Getter
    private static eu.zonecraft.pterocloud.utils.message.Logger logger;

    public static void main(String[] args) {
        instance = new PteroCloud();
        utils = new Utils();
        FileUtils.createFiles();
        logger = new eu.zonecraft.pterocloud.utils.message.Logger();
        instance.shutdownHook();

        logger = new eu.zonecraft.pterocloud.utils.message.Logger();
        instance.shutdownHook();

        instance.init();
    }

    public void init() {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);

        PteroCloud.getUtils().setScreenRunning(false);
        PteroCloud.getUtils().setAnimationFinished(false);
        PteroCloud.getUtils().setAnimationSuccess(false);

        MessageUtils.sendMessage(
                        Color.BLUE + "    ____  __                  ________                __\n" +
                        Color.BLUE + "   / __ \\/ /____  _________  / ____/ /___  __  ______/ /\n" +
                        Color.RESET + "  / /_/ / __/ _ \\/ ___/ __ \\/ /   / / __ \\/ / / / __  / \n" +
                        Color.RESET + " / ____/ /_/  __/ /  / /_/ / /___/ / /_/ / /_/ / /_/ /  \n" +
                        Color.RESET + "/_/    \\__/\\___/_/   \\____/\\____/_/\\____/\\__,_/\\__,_/   \n" +
                        "                                                        \n" +
                        Color.GRAY + "» " + Color.CYAN + "Version" + Color.GRAY + ": " + Color.BLUE + new VersionManager().getVersion() + "\n" +
                        Color.GRAY + "» " + Color.CYAN + "Developed by " + Color.BLUE + "ZoneCraft-Team\n");


        MessageUtils.sendAnimatedMessage(MessageType.INFO, "Loading CloudSystem...");

        commandManager = new CommandManager();
        screenManager = new ScreenManager();
        moduleManager = new ModuleManager();
        groupManager = new GroupManager();
        versionManager = new VersionManager();
        serviceManager = new ServiceManager();
        screenManager.registerScreen();
        utils.finishAnimation(true);


        Storage storage = new Storage(FileUtils.CONFIG_FILE);

        MessageUtils.sendAnimatedMessage(MessageType.INFO, "Loading Modules...");
        moduleManager.loadModules();
        utils.finishAnimation(true);

        MessageUtils.sendMessage(MessageType.INFO, "Registered Groups: " + Color.BLUE + getGroupManager().listRegisteredGroups());

        new PteroAuthManager(storage.getArray("pterodactylConfiguration", "host"),
                storage.getArray("pterodactylConfiguration", "clientKey"), storage.getArray("pterodactylConfiguration", "applicationKey"));


        MessageUtils.sendMessage(MessageType.INFO, "Successfully loaded PteroCloud!");

        groupManager.getGroups().forEach(group -> {
            if(groupManager.get(group, "isAutoStart").equals("true")) {
                new ServiceQueue(group).startServices();
                MessageUtils.sendMessage(MessageType.INFO + "Starting group" + Color.GRAY + ": " + Color.BLUE + group);
            }
        });
        commandManager.readCommand();
    }

    public void shutdownHook() {
        Thread printingHook = new Thread(() -> {
            MessageUtils.sendAnimatedMessage(MessageType.INFO, "Shutting down PteroCloud...");
            getServiceManager().CACHED_GROUP_SERVICES.forEach((serviceName, groupName) -> {
                MessageUtils.sendAnimatedMessage(MessageType.INFO, "Shutting down service" + Color.GRAY + ": " + Color.BLUE + serviceName + Color.RESET + " from group" + Color.GRAY + ": " + Color.BLUE + groupName);
                serviceManager.stopService(serviceName);
                utils.finishAnimation(true);
            });
            try {
                logger.fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            moduleManager.unloadModules();
            utils.finishAnimation(true);
        });
        Runtime.getRuntime().addShutdownHook(printingHook);
    }
}