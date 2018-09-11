package by.training.hotel.controller.command;

import by.training.hotel.controller.command.mapping.CommandManager;
import by.training.hotel.controller.command.mapping.UrlPattern;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public final class CommandFactory {

    private static final CommandFactory INSTANCE = new CommandFactory();

    private Map<String, Command> commandsMap;

    private CommandFactory() {
        CommandManager manager = CommandManager.getInstance();
        commandsMap = manager.getAllCommands();
    }

    public Command getCommand(HttpServletRequest request) {
        String action = request.getServletPath();
        Command command = commandsMap.get(action);
        if (command == null) {
            command = commandsMap.get(UrlPattern.HOME);
        }
        return command;
    }

    public static CommandFactory getInstance() {
        return INSTANCE;
    }
}
