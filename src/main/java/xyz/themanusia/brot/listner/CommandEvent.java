package xyz.themanusia.brot.listner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.themanusia.brot.helper.Helper;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommandEvent {
    private String name;
    private String rawBody;
    private ArrayList<String> args = new ArrayList<>();
    private Message message;
    private Guild guild;
    private MessageChannel channel;
    private User author;

    public CommandEvent(MessageReceivedEvent event) {
        String[] a = event.getMessage().getContentDisplay().split(" ", 2);
        name = Helper.removePrefix(a[0]);
        rawBody = a.length > 1 ? a[1] : null;
        message = event.getMessage();
        guild = event.getGuild();
        channel = event.getChannel();
        author = event.getAuthor();
    }
}
