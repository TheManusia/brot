package xyz.themanusia.brot.sauce;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import xyz.themanusia.brot.network.saucenao.SauceNaoCore;
import xyz.themanusia.brot.network.saucenao.callback.SauceCallback;
import xyz.themanusia.brot.network.saucenao.response.Sauce;

import java.awt.*;

public class SauceController implements SauceRepository {
    private final SauceNaoCore sauceNaoCore;

    public SauceController() {
        sauceNaoCore = new SauceNaoCore();
    }

    @Override
    public void getSauce(MessageChannel channel, String image) {
        channel.sendMessage(":mag: Searching...").queue(message ->
                sauceNaoCore.getSauce(image, new SauceCallback() {
                    @Override
                    public void onSuccess(Sauce sauce) {
                        String name = (sauce.getName() == null) ? "-" : sauce.getName();
                        message.editMessage(new EmbedBuilder()
                                .setColor(new Color(247, 239, 198))
                                .setTitle(sauce.getName(), sauce.getUrl())
                                .setImage(sauce.getThumbnail())
                                .addField("Similarity", sauce.getSimilarity(), true)
                                .addField("Name", name, true)
                                .build())
                                .append("Sauce Found")
                                .queue();
                    }

                    @Override
                    public void onFailure(String msg) {
                        message.editMessage(msg).queue();
                    }
                }));
    }
}
