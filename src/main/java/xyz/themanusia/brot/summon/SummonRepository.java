package xyz.themanusia.brot.summon;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface SummonRepository {
    void onStartSummon(SummonEntity se, MessageReceivedEvent event);

    void onCancelSummon(Guild guild, MessageChannel channel);

    void onSummoned(Guild guild, MessageChannel channel);

    boolean isSummon(User user, Guild guild);

    boolean isEmpty(Guild guild);
}
