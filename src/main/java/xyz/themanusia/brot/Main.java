package xyz.themanusia.brot;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import xyz.themanusia.brot.anime.AnimeListener;
import xyz.themanusia.brot.sauce.SauceListener;
import xyz.themanusia.brot.summon.SummonListener;

public class Main {
    public static String SAUCENAO_API;

    @SneakyThrows
    public static void main(String[] args) {
        String token = args[0];
        SAUCENAO_API = args[1];
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.setActivity(Activity.listening("BROTT MONCROTT"));

        JDA client = builder.build();
        client.addEventListener(new SummonListener());
        client.addEventListener(new AnimeListener());
        client.addEventListener(new SauceListener());

    }
}
