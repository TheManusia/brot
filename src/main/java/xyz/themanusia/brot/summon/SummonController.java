package xyz.themanusia.brot.summon;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.themanusia.brot.constant.DBColor;
import xyz.themanusia.brot.constant.DBText;
import xyz.themanusia.brot.constant.DBUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static xyz.themanusia.brot.constant.DBText.DELAY;

public class SummonController implements SummonRepository {
    final ArrayList<SummonEntity> arrayList = new ArrayList<>();

    private static final TimeUnit TIME = TimeUnit.SECONDS;

    @Override
    public void onStartSummon(SummonEntity se, MessageReceivedEvent event) {
        MessageChannel chnl = event.getChannel();
        Message summoning = new MessageBuilder()
                .setEmbeds(new EmbedBuilder()
                        .setColor(DBColor.EMBED_COLOR)
                        .setTitle(DBText.SUMMONING)
                        .setImage(DBUrl.START)
                        .build())
                .build();
        arrayList.add(se);
        chnl.sendMessage(summoning).queue(message -> {
            message.delete().queueAfter(DBText.DELAY, TIME);
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
                                c.sendMessage(DBText.SUMMONING_DM(summonEntity.getSummoner().getAsTag(),
                                                message.getGuild().getName()))
                                        .queue(s -> s.delete().queueAfter(DELAY, TIME)));
                        a++;
                    }
                } else {
                    doneSummon(new MessageBuilder()
                            .setEmbeds(new EmbedBuilder()
                                    .setColor(DBColor.EMBED_COLOR)
                                    .setImage(DBUrl.FAILED)
                                    .setTitle(DBText.CANT_SUMMON)
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
                        .setColor(DBColor.EMBED_COLOR)
                        .setTitle(DBText.CANCEL_SUMMON)
                        .setImage(DBUrl.CANCEL)
                        .build())
                .build(), guild, channel);
    }

    @Override
    public void onSummoned(Guild guild, MessageChannel channel) {
        doneSummon(new MessageBuilder()
                .setEmbeds(new EmbedBuilder()
                        .setColor(DBColor.EMBED_COLOR)
                        .setImage(DBUrl.SUCCESS)
                        .setTitle(DBText.USER_SUMMONED)
                        .setDescription(DBText.HAVE_A_NICE_DAY)
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
