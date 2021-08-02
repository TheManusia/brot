package xyz.themanusia.brot.listner;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.themanusia.brot.constant.DBText;
import xyz.themanusia.brot.helper.Helper;

import java.util.ArrayList;
import java.util.Arrays;

public class Client extends ListenerAdapter {
    private final ArrayList<Command> commands = new ArrayList<>();
    private final static Logger logger = LoggerFactory.getLogger(Client.class);

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        logger.debug("onMessageReceived run");
        String[] msg = event.getMessage().getContentDisplay().split(" ");
        if (!event.getAuthor().isBot()
                && event.getMessage().isFromGuild()
                && checkIsCommand(msg[0])) {
            String cmdName = Helper.removePrefix(msg[0]);
            Command command = checkCommand(cmdName);
            if (command != null)
                command.run(new CommandEvent(event));
        }
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void addCommands(Command... commands) {
        this.commands.addAll(Arrays.asList(commands));
    }

    private boolean checkIsCommand(String command) {
        String[] word = command.trim().split("");
        return word[0].equals(DBText.DEFAULT_PREFIX);
    }

    private Command checkCommand(String commandName) {
        for (Command command : commands) {
            if (command.name.equals(commandName)
                    || Arrays.asList(command.aliasses).contains(commandName))
                return command;
        }
        return null;
    }
}
