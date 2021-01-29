package xyz.themanusia.brot.summon;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Summon extends ListenerAdapter {
    private final SummonRepository summonRepository = new SummonController();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message msg = event.getMessage();
        MessageChannel chnl = event.getChannel();
        String[] cmd = msg.getContentDisplay().split(" ");

        if (cmd[0].contains("&summon")) {
            if (summonRepository.isEmpty()) {
                if (msg.getMentionedMembers().isEmpty()) {
                    chnl.sendMessage("Mention User!").queue();
                } else {
                    if (msg.getMentionedMembers().get(0).getUser().isBot()) {
                        chnl.sendMessage("Can't summon bot!").queue();
                    } else {
                        summonRepository.onStartSummon(new SummonEntity(
                                msg.getAuthor(),
                                msg.getMentionedMembers().get(0).getUser(),
                                false), event);
                    }
                }
            } else {
                chnl.sendMessage("Wait until user is summoned").queue();
            }
        } else if (cmd[0].contains("&cancel")) {
            summonRepository.onCancelSummon();
        } else if (summonRepository.isSummon(event.getAuthor())) {
            summonRepository.onSummoned();
        }
    }
}