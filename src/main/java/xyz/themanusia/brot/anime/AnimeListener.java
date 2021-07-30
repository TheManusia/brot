package xyz.themanusia.brot.anime;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import xyz.themanusia.brot.listener.BrotListenerAdapter;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AnimeListener extends BrotListenerAdapter {
    final AnimeRepository repository = new AnimeController();

    @SneakyThrows
    @Override
    public void onMessageChecked(@NotNull MessageReceivedEvent event) {
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
                } else if (msg[1].equalsIgnoreCase("-pic") || msg[1].equalsIgnoreCase("-picture")) {
                    if (!event.getMessage().getAttachments().isEmpty()) {
                        repository.searchAnimeWithPict(event.getChannel(), event.getMessage().getAttachments().get(0).getUrl());
                    } else if (msg.length > 2) {
                        repository.searchAnimeWithPict(event.getChannel(), msg[2]);
                    } else {
                        event.getChannel().sendMessage("Insert Picture!").queue();
                    }
                }
            } else {
                event.getChannel().sendMessage("Insert Parameter!").queue();
            }
        } else if (msg[0].contains("&manga")) {
            if (msg.length > 1) {
                if (msg[1].matches("[0-9]*")) {
                    repository.getMangaById(event.getChannel(), Integer.parseInt(msg[1]));
                } else if (msg[1].equalsIgnoreCase("-search") || msg[1].equalsIgnoreCase("-s")) {
                    String keyword = Arrays.stream(msg)
                            .filter(s -> !s.matches("(&anime)|(-search)|(-s)"))
                            .collect(Collectors.joining(" ")).trim();
                    repository.searchManga(event.getChannel(), keyword);
                }
            } else {
                event.getChannel().sendMessage("Insert Parameter!").queue();
            }
        }
    }
}
