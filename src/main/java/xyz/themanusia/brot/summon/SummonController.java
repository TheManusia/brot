package xyz.themanusia.brot.summon;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class SummonController implements SummonRepository {
    private SummonEntity summonEntity;
    private MessageReceivedEvent event;
    private MessageChannel chnl;
    private int a = 0;

    @Override
    public void onStartSummon(SummonEntity se, MessageReceivedEvent event) {
        summonEntity = se;
        this.event = event;
        chnl = event.getChannel();
        Message summoning = new MessageBuilder()
                .append("https://tenor.com/view/kuchiyose-no-jutsu-summoning-gif-13501965")
                .build();
        chnl.sendMessage(summoning).queue(message -> summoning());
    }

    @SneakyThrows
    private void summoning() {
        try {
            if (summonEntity != null) {
                if (a < 10) {
                    TimeUnit.SECONDS.sleep(3);
                    chnl.sendMessage(new MessageBuilder()
                            .append(summonEntity.getSummon()).build())
                            .queue();
                    summonEntity.getSummon().openPrivateChannel().queue(c -> c.sendMessage(new MessageBuilder().append("You're being summoned by ")
                            .append(summonEntity.getSummoner().getAsTag())
                            .build()).queue());
                    a++;
                } else {
                    summonEntity.setSummoned(true);
                    event.getChannel().sendMessage(new MessageBuilder()
                            .append("User can't be summoned").build())
                            .queue();
                    summonEntity = null;
                }
                if (summonEntity != null)
                    if (!summonEntity.isSummoned())
                        summoning();
            }
        } catch (NullPointerException ignore) {}
    }

    @Override
    public void onCancelSummon() {
        doneSummon(new MessageBuilder()
                .append("Summoning has been canceled")
                .build());
    }

    @Override
    public void onSummoned() {
        doneSummon(new MessageBuilder()
                .append("User has been summoned\n")
                .append("Have a nice day!")
                .build());
    }

    @Override
    public boolean isSummon(User user) {
        if (summonEntity != null)
            return summonEntity.getSummon().getId().equals(user.getId());
        return false;
    }

    @Override
    public boolean isEmpty() {
        return summonEntity == null;
    }

    private void doneSummon(Message msg) {
        summonEntity.setSummoned(true);
        chnl.sendMessage(msg).queue();
        summonEntity = null;
    }
}
