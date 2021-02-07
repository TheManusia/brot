package xyz.themanusia.brot.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

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
        String summon = "**summon** `user` - Summon user until the user exist\n" +
                "**cancel** - Cancel Summoning";

        String anime = "**anime** `id` - Show anime by id (You can get anime id from MyAnimeList)\n" +
                "**anime** `-s` | `-search` `keyword` - Search anime\n" +
                "**anime** `-pic` | `-picture` - Search anime with picture of anime scene";

        channel.sendMessage(new EmbedBuilder()
                .setTitle("Brot Command List")
                .setColor(new Color(247, 239, 198))
                .setDescription("Prefix for this bot is &")
                .addField("Summon Command", summon, false)
                .addField("Anime Command", anime, false)
                .build())
                .queue();
    }
}
