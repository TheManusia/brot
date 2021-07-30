package xyz.themanusia.brot.summon;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SummonController implements SummonRepository {
    final ArrayList<SummonEntity> arrayList = new ArrayList<>();

    private static final int DELAY = 3;
    private static final TimeUnit TIME = TimeUnit.SECONDS;

    private static final String START = "https://media.discordapp.net/attachments/790982389441757195/805430704430776340/tenor_4.gif";
    private static final String CANCEL = "https://media.discordapp.net/attachments/790982389441757195/805430646645719080/ezgif.com-gif-maker.gif";
    private static final String FAILED = "https://media.discordapp.net/attachments/790982389441757195/805430684592111636/tenor_3.gif";
    private static final String SUCCESS = "https://media.discordapp.net/attachments/790982389441757195/805430714258161724/ezgif-7-55441c58ca45.gif";

    @Override
    public void onStartSummon(SummonEntity se, MessageReceivedEvent event) {
        MessageChannel chnl = event.getChannel();
        Message summoning = new MessageBuilder()
                .setEmbeds(new EmbedBuilder()
                        .setColor(new Color(247, 239, 198))
                        .setTitle("Kuchiyose no Jutsu!")
                        .setImage(START)
                        .build())
                .build();
        arrayList.add(se);
        chnl.sendMessage(summoning).queue(message -> {
            message.delete().queueAfter(DELAY, TIME);
            summoning(message, se, 0);
        });
    }

    @SneakyThrows
    private void summoning(Message message, SummonEntity summonEntity, int a) {
        MessageChannel chnl = message.getChannel();
        try {
            if (summonEntity != null) {
                if (a < 10) {
                    TIME.sleep(DELAY);
                    if (!summonEntity.isSummoned()) {
                        chnl.sendMessage(new MessageBuilder()
                                .append(summonEntity.getSummon()).build())
                                .queue(s -> s.delete().queueAfter(DELAY, TIME));
                        summonEntity.getSummon().openPrivateChannel().queue(c ->
                                c.sendMessage(new MessageBuilder().append("You're being summoned by ")
                                        .append(summonEntity.getSummoner().getAsTag())
                                        .append(" in ")
                                        .append(message.getGuild().getName())
                                        .build()).queue(s -> s.delete().queueAfter(DELAY, TIME)));
                        a++;
                    }
                } else {
                    doneSummon(new MessageBuilder()
                            .setEmbeds(new EmbedBuilder()
                                    .setColor(new Color(247, 239, 198))
                                    .setImage(FAILED)
                                    .setTitle("User can't be summoned")
                                    .build()).build(), message.getGuild(), chnl);
                }
                if (!summonEntity.isSummoned())
                    summoning(message, summonEntity, a);
            }
        } catch (NullPointerException ignore) {
        }
    }

    @Override
    public void onCancelSummon(Guild guild, MessageChannel channel) {
        doneSummon(new MessageBuilder()
                .setEmbeds(new EmbedBuilder()
                        .setColor(new Color(247, 239, 198))
                        .setTitle("Summoning has been canceled")
                        .setImage(CANCEL)
                        .build())
                .build(), guild, channel);
    }

    @Override
    public void onSummoned(Guild guild, MessageChannel channel) {
        doneSummon(new MessageBuilder()
                .setEmbeds(new EmbedBuilder()
                        .setColor(new Color(247, 239, 198))
                        .setImage(SUCCESS)
                        .setTitle("User has been summoned")
                        .setDescription("Have a nice day!")
                        .build())
                .build(), guild, channel);
    }

    @Override
    public boolean isSummon(User user, Guild guild) {
        SummonEntity summonEntity = getEntity(guild);
        if (summonEntity != null)
            return summonEntity.getSummon().getId().equals(user.getId());
        return false;
    }

    @Override
    public boolean isEmpty(Guild guild) {
        return getEntity(guild) == null;
    }

    private void doneSummon(Message msg, Guild guild, MessageChannel chnl) {
        SummonEntity summonEntity = getEntity(guild);
        if (summonEntity != null) {
            summonEntity.setSummoned(true);
            chnl.sendMessage(msg).queue();
            arrayList.remove(summonEntity);
        }
    }

    private SummonEntity getEntity(Guild guild) {
        List<SummonEntity> summonEntity = arrayList.stream()
                .filter(s -> s.getGuildId().equals(guild.getId()))
                .collect(Collectors.toList());
        if (summonEntity.isEmpty())
            return null;
        return summonEntity.get(0);
    }
}
