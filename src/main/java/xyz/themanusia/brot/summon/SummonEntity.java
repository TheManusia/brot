package xyz.themanusia.brot.summon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.dv8tion.jda.api.entities.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SummonEntity {
    private String guildId;
    private User summoner;
    private User summon;
    private boolean isSummoned = false;
}
