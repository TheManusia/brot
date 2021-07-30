package xyz.themanusia.brot.constant;

import java.awt.*;

public class DBText {
    public final static String ANILIST_URL = "https://anilist.co/";

    public final static String USER_SUMMONED = "User has been summoned";
    public final static String HAVE_A_NICE_DAY = "Have a nice day!";
    public final static String SUMMONING = "Kuchiyose no Jutsu!";
    public final static String CANT_SUMMON = "User can't be summoned";
    public final static String CANCEL_SUMMON = "Summoning has been canceled";

    public final static String MENTION_USER = "Mention User!";
    public final static String BOT_WARNING = "Can't summon bot!";
    public final static String SUMMON_SELF = "You can't summon yourself";
    public final static String SUMMON_WAIT = "Wait until user is summoned";

    public final static String HELP_TITLE = "Brot Command List";
    public final static String BOT_PREFIX = "Prefix for this bot is &";

    public final static String SUMMON_TITLE = "Summon Command";
    public final static String SUMMON_COMMAND = "**summon** `user` - Summon user until the user exist\n" +
            "**cancel** - Cancel Summoning";

    public final static String ANIME_TITLE = "Anime Command";
    public final static String ANIME_COMMAND = "**anime** `id` - Show anime info by id (You can get anime id from MyAnimeList)\n" +
            "**anime** `-s` | `-search` `keyword` - Search anime\n" +
            "**anime** `-pic` | `-picture` `url` | `picture` - Search anime with picture of anime scene";

    public final static String MANGA_TITLE = "Manga Command";
    public final static String MANGA_COMMAND = "**manga** `id` - Show manga info by id (You can get manga id from MyAnimeList)\n" +
            "**manga** `-s` | `-search` `keyword` - Search manga";

    public final static String INSERT_PICTURE = "Insert Picture!";
    public final static String INSERT_PARAMETER = "Insert Parameter!";

    public final static String SAUCE_FOUND = "Sauce Found";
    public final static String ANIME_FOUND = "Anime Found";
    public final static String MANGA_FOUND = "Manga Found";

    public final static String SCORE = "Score";
    public final static String STATUS = "Status";
    public final static String GENRES = "Genres";
    public final static String RATING = "Rating";
    public final static String SYNOPSIS = "Synopsis";
    public final static String SIMILARITY = "Similarity";
    public final static String EPISODE = "Episode";
    public final static String EPISODES = "Episodes";
    public final static String VOLUMES = "Volumes";
    public final static String CHAPTERS = "Chapters";

    public final static String TOP_10 = "Top 10 Results";

    public final static String STAR_EMOJI = ":star:";
    public final static String SEARCHING = ":mag: Searching...";

    public final static String NO_SYNOPSIS = "No Synopsis";

    public static final int DELAY = 3;

    public static String SUMMONING_DM(String user, String guild) {
        return String.format("You are being summoned by %s at %s", user, guild);
    }

    public static String LIST_ITEM(int id, String name) {
        return String.format("%s %s", id, name);
    }

    public static String ANILIST_ANIME(int id) {
        return ANILIST_URL + id;
    }
}
