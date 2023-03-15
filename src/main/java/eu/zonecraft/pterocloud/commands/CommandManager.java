package eu.zonecraft.pterocloud.commands;

import eu.zonecraft.pterocloud.PteroCloud;
import eu.zonecraft.pterocloud.commands.impl.ClearCommand;
import eu.zonecraft.pterocloud.commands.impl.HelpCommand;
import eu.zonecraft.pterocloud.commands.impl.InfoCommand;
import eu.zonecraft.pterocloud.commands.impl.StopCommand;
import eu.zonecraft.pterocloud.utils.message.Color;
import eu.zonecraft.pterocloud.utils.message.MessageType;
import eu.zonecraft.pterocloud.utils.message.MessageUtils;

import java.util.Scanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CommandManager {

    private final HashMap<String, Command> commands_aliases = new HashMap<>();
    private final HashMap<String, Command> commands = new HashMap<>();

    private final Scanner scanner = new Scanner(System.in);

    public Thread commandThread;

    private void addCommmand(){
        registerCommand(new ClearCommand());
        registerCommand(new InfoCommand());
        registerCommand(new HelpCommand());
        registerCommand(new StopCommand());
    }

    public void registerCommand(Command command) {
        commands.put(command.getCommand().toLowerCase(), command);
        if (command.getAliases() != null) {
            for (String alias : command.getAliases()) {
                commands_aliases.put(alias.toLowerCase(), command);
            }
        }
    }

    public void readCommand() {
        addCommmand();
        commandThread = new Thread(() -> {
            while (!PteroCloud.getUtils().isScreenRunning) {
                System.out.print(Color.BLUE + "PteroCloud » " + Color.RESET);
                String s = scanner.nextLine();
                String[] input = s.split(" ");

                PteroCloud.getLogger().log("PteroCloud » " + s + "\n");
                if(s == null || s.isEmpty()) continue;

                if (commands_aliases.containsKey(input[0].toLowerCase()) || commands.containsKey(input[0].toLowerCase())) {

                    List<String> args = new ArrayList<>();

                    for (String i : input) {
                        if (!i.equalsIgnoreCase(input[0])) {
                            args.add(i);
                        }
                    }
                    if(commands.containsKey(input[0].toLowerCase())){
                        commands.get(input[0].toLowerCase()).onCommand(args.toArray(new String[0]));
                    }else{
                        commands_aliases.get(input[0].toLowerCase()).onCommand(args.toArray(new String[0]));
                    }
                } else {
                    MessageUtils.sendMessage(MessageType.WARNING, Color.RED + "Command not found");
                }
            }
        });
        commandThread.start();
    }

    public HashMap<String, Command> getCommands_aliases() {
        return commands_aliases;
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }
}
