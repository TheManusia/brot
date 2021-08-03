package xyz.themanusia.brot.listner;

import net.dv8tion.jda.api.entities.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.themanusia.brot.constant.DBText;

import java.util.Arrays;
import java.util.stream.Collectors;

import static xyz.themanusia.brot.listner.Command.ArgumentType.TEXT;

/**
 * All classes extending with this class will be executed if the {@link Command#name} of command same
 * with input from user
 */
public abstract class Command {
    /**
     * The name of the command, allows the command to be called the format: {@code [prefix]<command name>}.
     */
    protected String name = "";

    /**
     * Description of the command, will be showen on help command
     */
    protected String description = "No Description";

    /**
     * The aliases of the command, when this aliasses called will call {@link Command#name Command.name}
     */
    protected String[] aliasses = null;

    /**
     * Argument for command if command need value when called
     */
    protected String[] argument = null;

    /**
     * Type of argument, the arguments reference from {@link ArgumentType}
     */
    protected ArgumentType argType = null;

    private final static Logger logger = LoggerFactory.getLogger(Client.class);

    enum ArgumentType {
        /**
         * Type of argument when the argument is attachment
         */
        ATTACHMENTS,

        /**
         * Type of argument when the argument is text
         */
        TEXT,

        /**
         * Type of argument when the argument is text and attachment
         */
        BOTH
    }

    /**
     * Execute command
     *
     * @param event {@link CommandEvent}
     */
    protected abstract void execute(CommandEvent event);

    public void run(CommandEvent event) {
        logger.debug("Command run");
        if (argument != null) {
            if (event.getRawBody() == null) {
                event.getMessage().reply(DBText.INSERT_PARAMETER).queue();
                return;
            }

            String[] args = new String[]{};

            if (argType == null) argType = TEXT;

            switch (argType) {
                case ATTACHMENTS:
                    args = (String[]) event.getMessage().getAttachments().stream().map(Message.Attachment::getUrl).toArray();
                    break;
                case TEXT:
                    args = event.getRawBody().split(" ");
                    break;
                case BOTH:
                    if (!event.getMessage().getAttachments().isEmpty())
                        args = (String[]) event.getMessage().getAttachments().stream().map(Message.Attachment::getUrl).toArray();
                    else
                        args = event.getRawBody().split(" ");
                    break;
            }
            event.getArgs().addAll(Arrays.stream(args).collect(Collectors.toList()));
        }
        execute(event);
    }
}
