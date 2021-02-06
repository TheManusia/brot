package xyz.themanusia.brot.sauce;

import net.dv8tion.jda.api.entities.MessageChannel;

public interface SauceRepository {
    void getSauce(MessageChannel channel, String image);
}
