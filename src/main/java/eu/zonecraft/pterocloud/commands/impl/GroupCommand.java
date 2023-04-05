package eu.zonecraft.pterocloud.commands.impl;

import eu.zonecraft.pterocloud.PteroCloud;
import eu.zonecraft.pterocloud.commands.Command;
import eu.zonecraft.pterocloud.utils.message.Color;
import eu.zonecraft.pterocloud.utils.message.MessageType;
import eu.zonecraft.pterocloud.utils.message.MessageUtils;

public class GroupCommand extends Command {
    public GroupCommand() {
        super("group", "Manage Groups", "groups");
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("setup")) {
            PteroCloud.getInstance().getScreenManager().startScreen("groupSetup");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
            if (PteroCloud.getInstance().getGroupManager().groupExists(args[1])) {
                PteroCloud.getInstance().getGroupManager().deleteGroup(args[1]);
                MessageUtils.sendMessage(MessageType.INFO, "You have " + Color.GREEN + "successfully " + Color.RESET + "deleted the Group" + Color.GRAY + ": " + Color.BLUE + args[1]);
            } else {
                MessageUtils.sendMessage(MessageType.WARNING, "Group not found!");
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("list")) {
            if (PteroCloud.getInstance().getGroupManager().groupExists(args[1])) {
                MessageUtils.sendMessage(MessageType.INFO, PteroCloud.getInstance().getGroupManager().listRegisteredGroups());
            } else {
                MessageUtils.sendMessage(MessageType.WARNING, "Group not found!");
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
            if (PteroCloud.getInstance().getGroupManager().groupExists(args[1])) {
                PteroCloud.getInstance().getGroupManager().deleteGroup(args[1]);

            } else {

            }
        } else {
            MessageUtils.sendMessage("group setup " + Color.GRAY + "» " + Color.BLUE + "Create a new group");
            MessageUtils.sendMessage("group delete " + Color.GRAY + "(" + Color.BLUE + "Group" + Color.GRAY + ") " + Color.GRAY + "» " + Color.BLUE + "Create a new group");
            MessageUtils.sendMessage("group list " + Color.GRAY + "» " + Color.BLUE + "Lists all registered groups");

        }
    }
}