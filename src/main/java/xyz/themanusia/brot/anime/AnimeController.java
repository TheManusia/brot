package xyz.themanusia.brot.anime;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import xyz.themanusia.brot.network.jikan.JikanCore;
import xyz.themanusia.brot.network.jikan.callback.JikanAnimeCallback;
import xyz.themanusia.brot.network.jikan.callback.JikanAnimeSearchCallback;
import xyz.themanusia.brot.network.jikan.callback.JikanMangaCallback;
import xyz.themanusia.brot.network.jikan.callback.JikanMangaSearchCallback;
import xyz.themanusia.brot.network.jikan.response.*;

import java.util.stream.Collectors;

public class AnimeController implements AnimeRepository {
    JikanCore jikanCore;

    public AnimeController() {
        jikanCore = new JikanCore();
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
        return channel.sendMessage(":mag: Searching...");
    }

    private void sendMessageAnime(int id, Message message) {
        jikanCore.getAnimeById(id, new JikanAnimeCallback() {
            @Override
            public void onGetAnimeSuccess(Anime anime) {
                String synopsis = (anime.getSynopsis() == null) ? "No Synopsis" :
                        (anime.getSynopsis().length() > 415) ?
                                anime.getSynopsis().substring(0, 415) + "..." : anime.getSynopsis();
                String titleEn = (anime.getTitleEnglish() == null) ? anime.getTitle() : anime.getTitleEnglish();
                String genres = anime.getGenres().stream()
                        .map(Genre::getName)
                        .collect(Collectors.joining(", "));
                MessageEmbed embedBuilder = new EmbedBuilder()
                        .setTitle(anime.getTitle(), anime.getUrl())
                        .setImage(anime.getImageUrl())
                        .setDescription(anime.getMalId() + "#" + titleEn)
                        .addField("Score", ":star: " + anime.getScore(), true)
                        .addField("Status", anime.getStatus(), true)
                        .addField("Episodes", "" + anime.getEpisodes(), true)
                        .addField("Genres", genres, true)
                        .addField("Rating", anime.getRating(), true)
                        .addField("Synopsis", synopsis, false)
                        .build();
                message.editMessage(new MessageBuilder()
                        .setEmbed(embedBuilder)
                        .build())
                        .append("Anime Found")
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
                String synopsis = (manga.getSynopsis() == null) ? "No Synopsis" :
                        (manga.getSynopsis().length() > 415) ?
                                manga.getSynopsis().substring(0, 415) + "..." : manga.getSynopsis();
                String titleEn = (manga.getTitleEn() == null) ? manga.getTitle() : manga.getTitleEn();
                String genres = manga.getGenres().stream()
                        .map(Genre::getName)
                        .collect(Collectors.joining(", "));
                MessageEmbed embedBuilder = new EmbedBuilder()
                        .setTitle(manga.getTitle(), manga.getUrl())
                        .setImage(manga.getImg())
                        .setDescription(manga.getMalId() + "#" + titleEn)
                        .addField("Score", ":star: " + manga.getScore(), true)
                        .addField("Status", manga.getStatus(), true)
                        .addField("Volume", "" + manga.getVolumes(), true)
                        .addField("Genres", genres, true)
                        .addField("Chapter", "" + manga.getChapters(), true)
                        .addField("Synopsis", synopsis, false)
                        .build();
                message.editMessage(new MessageBuilder()
                        .setEmbed(embedBuilder)
                        .build())
                        .append("Manga Found")
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
                        .map(anime -> anime.getMalId() + "#" + anime.getTitle())
                        .limit(10)
                        .collect(Collectors.joining("\n"));
                MessageEmbed embedBuilder = new EmbedBuilder()
                        .setTitle("Top 10 Results")
                        .setDescription(result)
                        .build();
                message.editMessage(
                        new MessageBuilder()
                                .setEmbed(embedBuilder)
                                .append("Animes Found")
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
                        .map(manga -> manga.getMalId() + "#" + manga.getTitle())
                        .limit(10)
                        .collect(Collectors.joining("\n"));
                MessageEmbed embedBuilder = new EmbedBuilder()
                        .setTitle("Top 10 Results")
                        .setDescription(result)
                        .build();
                message.editMessage(
                        new MessageBuilder()
                                .setEmbed(embedBuilder)
                                .append("Mangas Found")
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
