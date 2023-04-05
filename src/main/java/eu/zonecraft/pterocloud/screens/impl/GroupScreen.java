package eu.zonecraft.pterocloud.screens.impl;

import eu.zonecraft.pterocloud.PteroCloud;
import eu.zonecraft.pterocloud.screens.Screen;
import eu.zonecraft.pterocloud.utils.GroupType;
import eu.zonecraft.pterocloud.utils.message.MessageType;
import eu.zonecraft.pterocloud.utils.message.MessageUtils;

import java.util.Scanner;

public class GroupScreen extends Screen {
    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);
        MessageUtils.sendMessage(MessageType.INPUT, "Please enter the name of the group: ");
        String name = scanner.nextLine();

        MessageUtils.sendMessage(MessageType.INPUT, "Please enter the memory of the group: ");
        int memory = 0;
        try {
            memory = scanner.nextInt();
        } catch (NumberFormatException e) {
            MessageUtils.sendMessage(MessageType.ERROR, "Please enter a valid number!");
        }

        MessageUtils.sendMessage(MessageType.INPUT, "Please enter the max. Players: ");
        int maxPlayers = 0;
        try {
            maxPlayers = scanner.nextInt();
        } catch (NumberFormatException e) {
            MessageUtils.sendMessage(MessageType.ERROR, "Please enter a valid number!");
        }

        MessageUtils.sendMessage(MessageType.INPUT, "Please enter the min. Online: ");
        int minOnline = 0;
        try {
            minOnline = scanner.nextInt();
        } catch (NumberFormatException e) {
            MessageUtils.sendMessage(MessageType.ERROR, "Please enter a valid number!");
        }

        MessageUtils.sendMessage(MessageType.INPUT, "Please enter the min. Online: ");
        int maxOnline = 0;
        try {
            maxOnline = scanner.nextInt();
        } catch (NumberFormatException e) {
            MessageUtils.sendMessage(MessageType.ERROR, "Please enter a valid number!");
        }

        MessageUtils.sendMessage(MessageType.INPUT, "Please enter if the group is static or not [true, false]: ");
        if (scanner.nextLine() != "true" || scanner.nextLine() != "false") {
            MessageUtils.sendMessage(MessageType.ERROR, "Please enter a valid value!");
        }
        boolean isStatic = scanner.nextBoolean();

        MessageUtils.sendMessage(MessageType.INPUT, "Please enter the type of the group [PROXY, LOBBY, SERVER]: ");
        GroupType type = null;
        if (GroupType.valueOf(scanner.nextLine()) == GroupType.PROXY
                || GroupType.valueOf(scanner.nextLine()) == GroupType.LOBBY || GroupType.valueOf(scanner.nextLine()) == GroupType.SERVER) {

            type = GroupType.valueOf(scanner.nextLine());
        } else {
            MessageUtils.sendMessage(MessageType.ERROR, "Please enter a valid type!");
        }

        PteroCloud.getInstance().getGroupManager().createGroup(name, memory, maxPlayers, minOnline, maxOnline, isStatic, true, type);
        MessageUtils.sendMessage(MessageType.INFO, "Group created successfully!");
        stop();
    }

    @Override
    public void stop() {
        PteroCloud.getInstance().init();
    }
}
