package eu.zonecraft.pterocloud.module;

import eu.zonecraft.pterocloud.PteroCloud;
import eu.zonecraft.pterocloud.utils.message.MessageType;
import eu.zonecraft.pterocloud.utils.message.MessageUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public static List<Module> modules = new ArrayList<>();

    public void loadModules() {
        File[] moduleFiles = new File("modules").listFiles();

        //Load plugins
        for(File f : moduleFiles) {
            if(f.isDirectory()) {
                continue;
            }

            if(!f.getName().endsWith(".jar")) {
                continue;
            }

            Module p = null;

            try {
                p = ModuleLoader.loadModule(f);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException |
                     InvocationTargetException | NoSuchMethodException | SecurityException | IOException e) {
                PteroCloud.getUtils().finishAnimation(false);
                MessageUtils.sendMessage(MessageType.ERROR, "Error while loading module " + f.getName() + "!");
                MessageUtils.sendMessage(MessageType.ERROR, "Error: " + e.getMessage());
                System.exit(1);
            }

            modules.add(p);
        }

        for(Module m : modules) {
            m.onEnable();
        }
    }

    public void unloadModules() {
        for(Module m : modules) {
            m.onDisable();
        }
    }

}