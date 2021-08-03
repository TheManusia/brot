package xyz.themanusia.brot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import xyz.themanusia.brot.constant.DBColor;
import xyz.themanusia.brot.constant.DBText;
import xyz.themanusia.brot.helper.Helper;
import xyz.themanusia.brot.listner.Command;
import xyz.themanusia.brot.listner.CommandEvent;
import xyz.themanusia.brot.network.jikan.JikanCore;
import xyz.themanusia.brot.network.jikan.callback.JikanAnimeCallback;
import xyz.themanusia.brot.network.jikan.callback.JikanAnimeSearchCallback;
import xyz.themanusia.brot.network.jikan.response.Anime;
import xyz.themanusia.brot.network.jikan.response.AnimeSearch;
import xyz.themanusia.brot.network.jikan.response.Genre;

import java.util.stream.Collectors;

public class AnimeCommand extends Command {
    private final JikanCore jikanCore;

    public AnimeCommand() {
        this.name = "anime";
        this.description = "Search anime by id or keyword";
        this.argType = ArgumentType.TEXT;
        this.argument = new String[]{"id|keyword"};

        jikanCore = new JikanCore();
    }

    @Override
    protected void execute(CommandEvent event) {
        event.getMessage().reply(DBText.SEARCHING).queue(s -> {
            if (event.getRawBody().matches("[0-9]*")) {
                sendMessageAnime(event.getArgs().get(0), s);
            } else {
                sendMessageAnimes(event.getRawBody(), s);
            }
        });
    }

    private void sendMessageAnime(String animeId, Message message) {
        int id = Integer.parseInt(animeId);
        jikanCore.getAnimeById(id, new JikanAnimeCallback() {
            @Override
            public void onGetAnimeSuccess(Anime anime) {
                String synopsis = Helper.isNull(anime.getSynopsis()) ? DBText.NO_SYNOPSIS :
                        Helper.stringMinimize(anime.getSynopsis());
                String titleEn = Helper.isNull(anime.getTitleEnglish()) ? anime.getTitle() : anime.getTitleEnglish();
                String genres = anime.getGenres().stream()
                        .map(Genre::getName)
                        .collect(Collectors.joining(", "));
                MessageEmbed embedBuilder = new EmbedBuilder()
                        .setColor(DBColor.EMBED_COLOR)
                        .setTitle(anime.getTitle(), anime.getUrl())
                        .setImage(anime.getImageUrl())
                        .setDescription(DBText.LIST_ITEM(anime.getMalId(), titleEn))
                        .addField(DBText.SCORE, DBText.STAR_EMOJI + anime.getScore(), true)
                        .addField(DBText.STATUS, anime.getStatus(), true)
                        .addField(DBText.EPISODES, Helper.toString(anime.getEpisodes()), true)
                        .addField(DBText.GENRES, genres, true)
                        .addField(DBText.RATING, anime.getRating(), true)
                        .addField(DBText.SYNOPSIS, synopsis, false)
                        .build();
                message.editMessage(new MessageBuilder()
                                .setEmbeds(embedBuilder)
                                .build())
                        .append(DBText.ANIME_FOUND)
                        .queue();
            }

            @Override
            public void onFailure(String msg) {
                message.editMessage(msg).queue();
            }
        });
    }

    private void sendMessageAnimes(String keyword, Message message) {
        jikanCore.searchAnime(keyword, new JikanAnimeSearchCallback() {
            @Override
            public void onGetAnimesSuccess(AnimeSearch results) {
                String result = results.getResults().stream()
                        .map(anime -> DBText.LIST_ITEM(anime.getMalId(), anime.getTitle()))
                        .limit(10)
                        .collect(Collectors.joining("\n"));
                MessageEmbed embedBuilder = new EmbedBuilder()
                        .setColor(DBColor.EMBED_COLOR)
                        .setTitle(DBText.TOP_10)
                        .setDescription(result)
                        .build();
                message.editMessage(
                                new MessageBuilder()
                                        .setEmbeds(embedBuilder)
                                        .append(DBText.ANIME_FOUND)
                                        .build())
                        .queue();
            }

            @Override
            public void onFailure(String msg) {
                message.editMessage(msg).queue();
            }
        });
    }

    public void getAnime(String id, Message message) {
        sendMessageAnime(id, message);
    }

    public static void getAnimeById(String id, Message message) {
        AnimeCommand cmd = new AnimeCommand();
        cmd.sendMessageAnime(id, message);
    }
}
