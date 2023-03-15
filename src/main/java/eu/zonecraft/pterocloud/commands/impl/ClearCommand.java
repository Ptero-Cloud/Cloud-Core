package eu.zonecraft.pterocloud.commands.impl;

import eu.zonecraft.pterocloud.PteroCloud;
import eu.zonecraft.pterocloud.commands.Command;

public class ClearCommand extends Command {
    public ClearCommand() {
        super("clear", "Clear the console", "cls");
    }

    @Override
    public void onCommand(String[] args) {
        PteroCloud.getUtils().clearConsole();
    }
}
