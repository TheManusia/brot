package xyz.themanusia.brot.listner;

import net.dv8tion.jda.api.entities.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.themanusia.brot.constant.DBText;

import java.util.Arrays;
import java.util.stream.Collectors;

import static xyz.themanusia.brot.listner.Command.ArgumentType.TEXT;

public abstract class Command {
    protected String name = "";
    protected String description = "No Description";
    protected String[] aliasses = null;
    protected String[] argument = null;
    protected ArgumentType argType = null;

    private final static Logger logger = LoggerFactory.getLogger(Client.class);

    enum ArgumentType {
        ATTACHMENTS,
        TEXT,
        BOTH
    }

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
