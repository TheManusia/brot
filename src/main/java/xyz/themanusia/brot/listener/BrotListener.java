package xyz.themanusia.brot.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public interface BrotListener {
    void onMessageChecked(@NotNull MessageReceivedEvent event);
}
