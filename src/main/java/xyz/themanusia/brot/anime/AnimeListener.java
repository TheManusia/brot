package xyz.themanusia.brot.anime;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AnimeListener extends ListenerAdapter {
    AnimeRepository repository = new AnimeController();

    @SneakyThrows
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String[] msg = event.getMessage().getContentDisplay().split(" ");
        if (msg[0].contains("&anime")) {
            if (msg.length > 1) {
                if (msg[1].matches("[0-9]*")) {
                    repository.getAnimeById(event.getChannel(), Integer.parseInt(msg[1]));
                } else if (msg[1].equalsIgnoreCase("-search") || msg[1].equalsIgnoreCase("-s")) {
                    String keyword = Arrays.stream(msg)
                            .filter(s -> !s.matches("(&anime)|(-search)|(-s)"))
                            .collect(Collectors.joining(" ")).trim();
                    repository.searchAnime(event.getChannel(), keyword);
                }
            } else {
                event.getChannel().sendMessage("Insert Parameter!").queue();
            }
        }
    }
}
