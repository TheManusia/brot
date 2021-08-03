package xyz.themanusia.brot.commands;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import xyz.themanusia.brot.constant.DBColor;
import xyz.themanusia.brot.constant.DBText;
import xyz.themanusia.brot.helper.Helper;
import xyz.themanusia.brot.listner.Command;
import xyz.themanusia.brot.listner.CommandEvent;
import xyz.themanusia.brot.network.tracemoe.TraceMoeCore;
import xyz.themanusia.brot.network.tracemoe.callback.TraceMoeCallback;
import xyz.themanusia.brot.network.tracemoe.response.Sauce;

public class SceneCommand extends Command {
    private final TraceMoeCore traceMoeCore;

    public SceneCommand() {
        this.name = "scene";
        this.description = "Search anime by uploaded picture of scene";
        this.argType = ArgumentType.BOTH;
        this.argument = new String[]{"picture|url"};

        traceMoeCore = new TraceMoeCore();
    }

    @Override
    protected void execute(CommandEvent event) {
        event.getMessage().reply(DBText.SEARCHING).queue(s ->
                searchAnimeWithPict(s, event.getArgs().get(0)));
    }

    public void searchAnimeWithPict(Message message, String image) {
        traceMoeCore.getSauce(image, new TraceMoeCallback() {
            @SneakyThrows
            @Override
            public void onSuccess(Sauce sauce) {
                message.editMessage(new MessageBuilder()
                                .append(DBText.SAUCE_FOUND)
                                .setEmbeds(new EmbedBuilder()
                                        .setTitle(sauce.getAnilist().getTitle().getRomajiTitle(), DBText.ANILIST_ANIME(sauce.getAnilist().getIdMal()))
                                        .setImage(sauce.getImageUrl())
                                        .setColor(DBColor.EMBED_COLOR)
                                        .addField(DBText.SIMILARITY, Helper.percentage(sauce.getSimilarity()), true)
                                        .addField(DBText.EPISODE, String.valueOf(sauce.getEpisode()), true)
                                        .build())
                                .build())
                        .queue();

                message.getChannel().sendMessage(DBText.GETTING_INFO)
                        .queue(msg -> AnimeCommand.getAnimeById(String.valueOf(sauce.getAnilist().getIdMal()), msg));
            }

            @Override
            public void onFailure(String msg) {
                message.editMessage(msg).queue();
            }
        });
    }

}
