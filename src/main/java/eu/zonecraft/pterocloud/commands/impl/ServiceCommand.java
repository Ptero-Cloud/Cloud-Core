package eu.zonecraft.pterocloud.commands.impl;

import eu.zonecraft.pterocloud.PteroCloud;
import eu.zonecraft.pterocloud.commands.Command;
import eu.zonecraft.pterocloud.utils.message.Color;
import eu.zonecraft.pterocloud.utils.message.MessageType;
import eu.zonecraft.pterocloud.utils.message.MessageUtils;

public class ServiceCommand extends Command {
    public ServiceCommand() {
        super("service", "Start gameserver", "ser");
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length == 2 && args[0].equalsIgnoreCase("start")) {
            if (PteroCloud.getInstance().getServiceManager().serviceExists(args[1])) {
                MessageUtils.sendAnimatedMessage(MessageType.INFO, "Starting service" + Color.GRAY + ": " + Color.BLUE + args[1] + Color.RESET + "...");
                try {
                    PteroCloud.getInstance().getServiceManager().startService(args[1]);
                    PteroCloud.getUtils().finishAnimation(true);
                } catch (Exception e) {
                    PteroCloud.getUtils().finishAnimation(false);
                }
            } else {
                MessageUtils.sendMessage(MessageType.WARNING + "Service not found!");
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("stop")) {
            if (PteroCloud.getInstance().getServiceManager().serviceExists(args[1])) {
                MessageUtils.sendAnimatedMessage(MessageType.INFO, "Stopping service" + Color.GRAY + ": " + Color.BLUE + args[1] + Color.RESET + "...");
                try {
                    PteroCloud.getInstance().getServiceManager().stopService(args[1]);
                    PteroCloud.getUtils().finishAnimation(true);
                } catch (Exception e) {
                    PteroCloud.getUtils().finishAnimation(false);
                }
            } else {
                MessageUtils.sendMessage(MessageType.WARNING + "Service not found!");
            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
            MessageUtils.sendMessage(PteroCloud.getInstance().getServiceManager().listRunningServices());
        } else {
            MessageUtils.sendMessage("service start " + Color.GRAY + "(" + Color.BLUE + "Group" + Color.GRAY + ") " + Color.GRAY + "» " + Color.BLUE + "Starts a service from a group");
            MessageUtils.sendMessage("service stop " + Color.GRAY + "(" + Color.BLUE + "Group" + Color.GRAY + ") " + Color.GRAY + "» " + Color.BLUE + "Stops a service from a group");
            MessageUtils.sendMessage("service list " + Color.GRAY + "» " + Color.BLUE + "Lists all running services");
        }
    }
}
