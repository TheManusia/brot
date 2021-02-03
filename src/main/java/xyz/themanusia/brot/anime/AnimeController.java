package xyz.themanusia.brot.anime;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import xyz.themanusia.brot.network.jikan.JikanCore;
import xyz.themanusia.brot.network.jikan.callback.JikanAnimeCallback;
import xyz.themanusia.brot.network.jikan.response.Anime;
import xyz.themanusia.brot.network.jikan.response.Genre;

import java.util.stream.Collectors;

public class AnimeController implements AnimeRepository {
    JikanCore jikanCore;

    @Override
    public void getAnimeById(MessageChannel channel, int id) {
        jikanCore = new JikanCore();
        channel.sendMessage(":mag: Searching...").queue(message -> sendMessageAnime(id, message));

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
}
