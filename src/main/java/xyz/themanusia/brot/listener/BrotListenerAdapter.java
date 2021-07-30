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

        String anime = "**anime** `id` - Show anime info by id (You can get anime id from MyAnimeList)\n" +
                "**anime** `-s` | `-search` `keyword` - Search anime\n" +
                "**anime** `-pic` | `-picture` `url` | `picture` - Search anime with picture of anime scene";

        String manga = "**manga** `id` - Show manga info by id (You can get manga id from MyAnimeList)\n" +
                "**manga** `-s` | `-search` `keyword` - Search manga";

        channel.sendMessageEmbeds(new EmbedBuilder()
                .setTitle("Brot Command List")
                .setColor(new Color(247, 239, 198))
                .setDescription("Prefix for this bot is &")
                .addField("Summon Command", summon, false)
                .addField("Anime Command", anime, false)
                .addField("Manga Command", manga, false)
                .build())
                .queue();
    }
}
