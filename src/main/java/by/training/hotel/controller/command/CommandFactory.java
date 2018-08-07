package by.training.hotel.controller.command;

import by.training.hotel.controller.command.mapping.CommandManager;

import java.util.Map;

public final class CommandFactory {

    private static final CommandFactory INSTANCE = new CommandFactory();

    private Map<String, Command> commandsMap;

    private CommandFactory() {
        CommandManager manager = CommandManager.getInstance();
        commandsMap = manager.getAllCommands();
    }

    public Command getCommand(String actionName){
        return commandsMap.get(actionName);
    }

    public static CommandFactory getInstance() {
        return INSTANCE;
    }
}
