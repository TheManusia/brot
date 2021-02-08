package xyz.themanusia.brot.network.tracemoe.response;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Sauce {

    @SerializedName("title_romaji")
    private String title;

    @SerializedName("episode")
    private String episode;

    @SerializedName("mal_id")
    private String malId;

    @SerializedName("similarity")
    private String similarity;

    @SerializedName("at")
    private String at;

    @SerializedName("anilist_id")
    private String aniListId;

    @SerializedName("filename")
    private String filename;

    @SerializedName("tokenthumb")
    private String tokenthumb;

    public int getEpisode() {
        if (episode == null || episode.equals("")) {
            return 0;
        }

        return Integer.parseInt(episode);
    }

    public int getMalId() {
        if (malId == null || malId.equals("")) {
            return 0;
        }

        return Integer.parseInt(malId);
    }

    public double getSimilarity() {
        if (similarity == null || similarity.equals("")) {
            return 0;
        }

        return Double.parseDouble(similarity);
    }

    public double getAt() {
        if (at == null || at.equals("")) {
            return 0;
        }
        return Double.parseDouble(at);
    }

    public int getAniListId() {
        if (aniListId == null || aniListId.equals("")) {
            return 0;
        }
        return Integer.parseInt(aniListId);
    }
}