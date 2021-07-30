package xyz.themanusia.brot.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import xyz.themanusia.brot.constant.DBColor;
import xyz.themanusia.brot.constant.DBText;

import java.awt.*;

public class BrotListenerAdapter extends ListenerAdapter implements BrotListener {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getMessage().isFromGuild()) {
            if (!event.getAuthor().isBot()) {
                onMessageChecked(event);
            }
        }
    }

    @Override
    public void onMessageChecked(@NotNull MessageReceivedEvent event) {
        if (event.getMessage().getContentDisplay().contains("&help")) {
            showHelp(event.getChannel());
        }
    }

    private void showHelp(MessageChannel channel) {

        channel.sendMessageEmbeds(new EmbedBuilder()
                .setTitle(DBText.HELP_TITLE)
                .setColor(DBColor.EMBED_COLOR)
                .setDescription(DBText.BOT_PREFIX)
                .addField(DBText.SUMMON_TITLE, DBText.SUMMON_COMMAND, false)
                .addField(DBText.ANIME_TITLE, DBText.ANIME_COMMAND, false)
                .addField(DBText.MANGA_TITLE, DBText.MANGA_COMMAND, false)
                .build())
                .queue();
    }
}
