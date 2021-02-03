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
import xyz.themanusia.brot.network.jikan.response.Anime;
import xyz.themanusia.brot.network.jikan.response.AnimeSearch;
import xyz.themanusia.brot.network.jikan.response.Genre;

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
}
