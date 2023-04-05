package eu.zonecraft.pterocloud.commands.impl;

import eu.zonecraft.pterocloud.PteroCloud;
import eu.zonecraft.pterocloud.commands.Command;
import eu.zonecraft.pterocloud.utils.message.Color;
import eu.zonecraft.pterocloud.utils.message.MessageType;
import eu.zonecraft.pterocloud.utils.message.MessageUtils;

public class InfoCommand extends Command {
    public InfoCommand() {
        super("info", "Prints information about the CloudSystem");
    }

    Runtime runtime = Runtime.getRuntime();

    @Override
    public void onCommand(String[] args) {
        MessageUtils.sendMessage(MessageType.INFO, "Version" + Color.GRAY + ": " + Color.BLUE + PteroCloud.getInstance().getVersionManager().getVersion());
        MessageUtils.sendMessage(MessageType.INFO, "Operating System" + Color.GRAY + ": " + Color.BLUE + System.getProperty("os.name"));
        MessageUtils.sendMessage(MessageType.INFO, "Memory Usage" + Color.GRAY + ": " + Color.BLUE + runtime.freeMemory() / 1024L / 1024L + "MB " + Color.GRAY +"/" + Color.BLUE + runtime.maxMemory() / 1024L / 1024L + "MB");
    }
}
