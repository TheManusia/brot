package xyz.themanusia.brot.anime;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import xyz.themanusia.brot.constant.DBColor;
import xyz.themanusia.brot.constant.DBText;
import xyz.themanusia.brot.helper.Helper;
import xyz.themanusia.brot.network.jikan.JikanCore;
import xyz.themanusia.brot.network.jikan.callback.JikanAnimeCallback;
import xyz.themanusia.brot.network.jikan.callback.JikanAnimeSearchCallback;
import xyz.themanusia.brot.network.jikan.callback.JikanMangaCallback;
import xyz.themanusia.brot.network.jikan.callback.JikanMangaSearchCallback;
import xyz.themanusia.brot.network.jikan.response.*;
import xyz.themanusia.brot.network.tracemoe.TraceMoeCore;
import xyz.themanusia.brot.network.tracemoe.callback.TraceMoeCallback;
import xyz.themanusia.brot.network.tracemoe.response.Sauce;

import java.util.stream.Collectors;

public class AnimeController implements AnimeRepository {
    final JikanCore jikanCore;
    final TraceMoeCore traceMoeCore;

    public AnimeController() {
        jikanCore = new JikanCore();
        traceMoeCore = new TraceMoeCore();
    }

    @Override
    public void getAnimeById(MessageChannel channel, int id) {
        searching(channel).queue(message ->
                sendMessageAnime(id, message)
        );
    }

    @Override
    public void searchAnime(MessageChannel channel, String keyword) {
        searching(channel).queue(message ->
                sendMessageAnimes(keyword, message)
        );
    }

    @Override
    public void searchAnimeWithPict(MessageChannel channel, String image) {
        searching(channel).queue(message -> traceMoeCore.getSauce(image, new TraceMoeCallback() {
            @SneakyThrows
            @Override
            public void onSuccess(Sauce sauce) {
                message.editMessage(new MessageBuilder()
                                .append(DBText.SAUCE_FOUND)
                                .setEmbeds(new EmbedBuilder()
                                        .setTitle(sauce.getAnilist().getTitle().getRomajiTitle(), DBText.ANILIST_ANIME(sauce.getAnilist().getId()))
                                        .setImage(sauce.getImageUrl())
                                        .setColor(DBColor.EMBED_COLOR)
                                        .addField(DBText.SIMILARITY, Helper.percentage(sauce.getSimilarity()), true)
                                        .addField(DBText.EPISODE, String.valueOf(sauce.getEpisode()), true)
                                        .build())
                                .build())
                        .queue();
            }

            @Override
            public void onFailure(String msg) {
                message.editMessage(msg).queue();
            }
        }));
    }

    @Override
    public void getMangaById(MessageChannel channel, int id) {
        searching(channel).queue(message ->
                sendMessageManga(id, message)
        );
    }

    @Override
    public void searchManga(MessageChannel channel, String keyword) {
        searching(channel).queue(message ->
                sendMessageMangas(keyword, message)
        );
    }

    private MessageAction searching(MessageChannel channel) {
        return channel.sendMessage(DBText.SEARCHING);
    }

    private void sendMessageAnime(int id, Message message) {
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

    private void sendMessageManga(int id, Message message) {
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
