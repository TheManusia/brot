package xyz.themanusia.brot.summon;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface SummonRepository {
    void onStartSummon(SummonEntity se, MessageReceivedEvent event);
    void onCancelSummon();
    void onSummoned();
    boolean isSummon(User user);
    boolean isEmpty();
}
