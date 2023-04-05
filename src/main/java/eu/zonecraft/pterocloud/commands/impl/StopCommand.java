package eu.zonecraft.pterocloud.commands.impl;

import eu.zonecraft.pterocloud.commands.Command;

public class StopCommand extends Command {
    public StopCommand() {
        super("stop", "Stop the server", "shutdown", "exit");
    }

    @Override
    public void onCommand(String[] args) {
        System.exit(0);
    }

}
