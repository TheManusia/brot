package xyz.themanusia.brot;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import xyz.themanusia.brot.summon.Summon;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        String token = args[0];
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.setActivity(Activity.watching("for sauce"));

        JDA client = builder.build();
        client.addEventListener(new Summon());

    }
}
