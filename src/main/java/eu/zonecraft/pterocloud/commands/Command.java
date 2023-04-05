package eu.zonecraft.pterocloud.commands;

import lombok.Getter;

@Getter
public abstract class Command {

    private final String command;
    private final String[] aliases;
    private final String description;

    public Command(String command, String description, String... aliases) {
        this.command = command;
        this.aliases = aliases;
        this.description = description;
    }

    public Command(String command, String description) {
        this.command = command;
        this.aliases = null;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getDescription() {
        return description;
    }

    public abstract void onCommand(String[] args);


}
