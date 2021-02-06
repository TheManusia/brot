package xyz.themanusia.brot.sauce;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class SauceListener extends ListenerAdapter {
    private final SauceRepository sauceRepository = new SauceController();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message msg = event.getMessage();
        if (msg.getContentDisplay().contains("&sauce")) {
            if (!msg.getAttachments().isEmpty() && event.getAuthor().getId().equals("320376899069149184")) {
                if (msg.getAttachments().get(0).isImage()) {
                    sauceRepository.getSauce(event.getChannel(), msg.getAttachments().get(0).getUrl());
                } else {
                    event.getChannel().sendMessage("Insert Images!").queue();
                }
            } else {
                event.getChannel().sendMessage("Insert Images!").queue();
            }
        }
    }
}
