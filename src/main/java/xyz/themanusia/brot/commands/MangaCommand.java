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
import xyz.themanusia.brot.network.jikan.callback.JikanMangaCallback;
import xyz.themanusia.brot.network.jikan.callback.JikanMangaSearchCallback;
import xyz.themanusia.brot.network.jikan.response.Genre;
import xyz.themanusia.brot.network.jikan.response.Manga;
import xyz.themanusia.brot.network.jikan.response.MangaSearch;

import java.util.stream.Collectors;

public class MangaCommand extends Command {
    private final JikanCore jikanCore;

    public MangaCommand() {
        this.name = "manga";
        this.description = "Search manga by id or keyword";
        this.argument = new String[]{"id|keyword"};
        this.argType = ArgumentType.TEXT;

        jikanCore = new JikanCore();
    }

    @Override
    protected void execute(CommandEvent event) {
        event.getMessage().reply(DBText.SEARCHING).queue(s -> {
            if (event.getRawBody().matches("[0-9]*")) {
                sendMessageManga(event.getArgs().get(0), s);
            } else {
                sendMessageMangas(event.getRawBody(), s);
            }
        });
    }

    private void sendMessageManga(String mangaId, Message message) {
        int id = Integer.parseInt(mangaId);
        jikanCore.getMangaById(id, new JikanMangaCallback() {
            @Override
            public void onGetMangaSuccess(Manga manga) {
                String synopsis = Helper.isNull(manga.getSynopsis()) ? DBText.NO_SYNOPSIS :
                        Helper.stringMinimize(manga.getSynopsis());
                String titleEn = Helper.isNull(manga.getTitleEn()) ? manga.getTitle() : manga.getTitleEn();
                String genres = manga.getGenres().stream()
                        .map(Genre::getName)
                        .collect(Collectors.joining(", "));
                MessageEmbed embedBuilder = new EmbedBuilder()
                        .setColor(DBColor.EMBED_COLOR)
                        .setTitle(manga.getTitle(), manga.getUrl())
                        .setImage(manga.getImg())
                        .setDescription(DBText.LIST_ITEM(manga.getMalId(), titleEn))
                        .addField(DBText.SCORE, DBText.STAR_EMOJI + manga.getScore(), true)
                        .addField(DBText.STATUS, manga.getStatus(), true)
                        .addField(DBText.VOLUMES, Helper.toString(manga.getVolumes()), true)
                        .addField(DBText.GENRES, genres, true)
                        .addField(DBText.CHAPTERS, Helper.toString(manga.getChapters()), true)
                        .addField(DBText.SYNOPSIS, synopsis, false)
                        .build();
                message.editMessage(new MessageBuilder()
                                .setEmbeds(embedBuilder)
                                .build())
                        .append(DBText.MANGA_FOUND)
                        .queue();
            }

            @Override
            public void onFailure(String msg) {
                message.editMessage(msg).queue();
            }
        });
    }

    private void sendMessageMangas(String keyword, Message message) {
        jikanCore.searchManga(keyword, new JikanMangaSearchCallback() {
            @Override
            public void onGetMangasSuccess(MangaSearch results) {
                String result = results.getResults().stream()
                        .map(manga -> DBText.LIST_ITEM(manga.getMalId(), manga.getTitle()))
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
                                        .append(DBText.MANGA_FOUND)
                                        .build())
                        .queue();
            }

            @Override
            public void onFailure(String msg) {
                message.editMessage(msg).queue();
            }
        });
    }

}
