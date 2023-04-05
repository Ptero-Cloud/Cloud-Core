package eu.zonecraft.pterocloud.screens;

import eu.zonecraft.pterocloud.PteroCloud;
import eu.zonecraft.pterocloud.screens.impl.GroupScreen;
import eu.zonecraft.pterocloud.screens.impl.SetupScreen;

import java.util.HashMap;

public class ScreenManager {

    HashMap<String, Screen> screens = new HashMap<>();

    public void registerScreen() {
        screens.put("setup", new SetupScreen());
        screens.put("groupSetup", new GroupScreen());
    }

    public void startScreen(String name) {
        PteroCloud.getUtils().setScreenRunning(true);
        screens.get(name).start();
    }

    public void stopScreen(String name) {
        PteroCloud.getUtils().setScreenRunning(false);
        screens.get(name).stop();
    }

    public Screen getScreen(String name) {
        return screens.get(name);
    }
}
