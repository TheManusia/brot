package xyz.themanusia.brot.summon;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import xyz.themanusia.brot.constant.DBText;
import xyz.themanusia.brot.listener.BrotListenerAdapter;

public class SummonListener extends BrotListenerAdapter {
    private final SummonRepository summonRepository = new SummonController();

    @Override
    public void onMessageChecked(@NotNull MessageReceivedEvent event) {
        Message msg = event.getMessage();
        MessageChannel chnl = event.getChannel();
        String[] cmd = msg.getContentDisplay().split(" ");

        if (cmd[0].contains("&summon")) {
            if (summonRepository.isEmpty(event.getGuild())) {
                if (msg.getMentionedMembers().isEmpty()) {
                    chnl.sendMessage(DBText.MENTION_USER).queue();
                } else {
                    if (msg.getMentionedMembers().get(0).getUser().isBot()) {
                        chnl.sendMessage(DBText.BOT_WARNING).queue();
                    } else {
                        if (msg.getMentionedMembers().get(0).getUser() == msg.getAuthor()) {
                            chnl.sendMessage(DBText.SUMMON_SELF).queue();
                        } else {
                            summonRepository.onStartSummon(new SummonEntity(
                                    event.getGuild().getId(),
                                    msg.getAuthor(),
                                    msg.getMentionedMembers().get(0).getUser(),
                                    false), event);
                        }
                    }
                }
            } else {
                chnl.sendMessage(DBText.SUMMON_WAIT).queue();
            }
        } else if (cmd[0].contains("&cancel")) {
            summonRepository.onCancelSummon(event.getGuild(), event.getChannel());
        } else if (summonRepository.isSummon(event.getAuthor(), event.getGuild())) {
            summonRepository.onSummoned(event.getGuild(), event.getChannel());
        }
    }
}
