package eu.zonecraft.pterocloud.screens.impl;

import eu.zonecraft.pterocloud.PteroCloud;
import eu.zonecraft.pterocloud.screens.Screen;

import java.util.Scanner;

public class SetupScreen extends Screen {

    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);

        stop();
    }

    @Override
    public void stop() {
        PteroCloud.getInstance().init();
    }
}
