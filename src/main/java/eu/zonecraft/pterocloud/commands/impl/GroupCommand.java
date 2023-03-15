package eu.zonecraft.pterocloud.commands.impl;

import eu.zonecraft.pterocloud.PteroCloud;
import eu.zonecraft.pterocloud.commands.Command;

public class GroupCommand extends Command {
    public GroupCommand() {
        super("group", "Manage Groups", "groups");
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("setup")) {

        } else if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
            if (PteroCloud.getInstance().getGroupManager().groupExists(args[1])) {
                PteroCloud.getInstance().getGroupManager().deleteGroup(args[1]);

            } else {

            }
        }
    }
}
