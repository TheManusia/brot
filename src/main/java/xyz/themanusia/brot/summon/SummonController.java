package xyz.themanusia.brot.summon;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SummonController implements SummonRepository {
    ArrayList<SummonEntity> arrayList = new ArrayList<>();

    private static final int DELAY = 3;
    private static final TimeUnit TIME = TimeUnit.SECONDS;

    @Override
    public void onStartSummon(SummonEntity se, MessageReceivedEvent event) {
        MessageChannel chnl = event.getChannel();
        Message summoning = new MessageBuilder()
                .append("https://tenor.com/view/kuchiyose-no-jutsu-summoning-gif-13501965")
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
                            .append("User can't be summoned").build(), message.getGuild(), chnl);
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
                .append("Summoning has been canceled")
                .build(), guild, channel);
    }

    @Override
    public void onSummoned(Guild guild, MessageChannel channel) {
        doneSummon(new MessageBuilder()
                .append("User has been summoned\n")
                .append("Have a nice day!")
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
