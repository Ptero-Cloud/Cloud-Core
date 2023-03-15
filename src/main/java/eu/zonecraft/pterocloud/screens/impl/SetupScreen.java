package eu.zonecraft.pterocloud.screens.impl;

import eu.zonecraft.pterocloud.PteroCloud;
import eu.zonecraft.pterocloud.screens.Screen;
import eu.zonecraft.pterocloud.utils.message.MessageType;
import eu.zonecraft.pterocloud.utils.message.MessageUtils;

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
