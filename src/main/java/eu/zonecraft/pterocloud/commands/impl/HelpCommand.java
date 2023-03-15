package eu.zonecraft.pterocloud.commands.impl;

import eu.zonecraft.pterocloud.PteroCloud;
import eu.zonecraft.pterocloud.commands.Command;
import eu.zonecraft.pterocloud.commands.CommandManager;
import eu.zonecraft.pterocloud.utils.message.Color;
import eu.zonecraft.pterocloud.utils.message.MessageType;
import eu.zonecraft.pterocloud.utils.message.MessageUtils;

import java.util.Arrays;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", "Print this message", "?");
    }

    @Override
    public void onCommand(String[] args) {
        MessageUtils.sendMessage(MessageType.INFO, "Available commands:");
        int i = 1;
        for (Command command : PteroCloud.getCommandManager().getCommands().values()) {

            if (command.getAliases() == null) {
                MessageUtils.sendMessage(Color.GREEN + i + ". " + Color.BLUE + command.getCommand() + Color.RESET + " » " + Color.RESET + command.getDescription());
                continue;
            }
            String aliases = Arrays.toString(command.getAliases()).replace("[", "").replace("]", "");
            MessageUtils.sendMessage(Color.GREEN + i + ". " + Color.BLUE + command.getCommand() + Color.RESET + " (" + Color.BLUE + aliases + Color.RESET + ") » " + Color.RESET + command.getDescription());
            i++;
        }
    }
}
