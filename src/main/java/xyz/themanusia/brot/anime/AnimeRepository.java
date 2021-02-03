package xyz.themanusia.brot.anime;

import net.dv8tion.jda.api.entities.MessageChannel;

public interface AnimeRepository {
    void getAnimeById(MessageChannel channel, int id);

    void searchAnime(MessageChannel channel, String keyword);
}
