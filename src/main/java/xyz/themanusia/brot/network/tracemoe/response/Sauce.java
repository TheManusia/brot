package xyz.themanusia.brot.network.tracemoe.response;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Sauce {

    @SerializedName("anilist")
    private String aniListId;

    @SerializedName("filename")
    private String filename;

    @SerializedName("episode")
    private String episode;

    @SerializedName("similarity")
    private String similarity;

    @SerializedName("image")
    private String imageUrl;

    public int getEpisode() {
        if (episode == null || episode.equals("")) {
            return 0;
        }

        return Integer.parseInt(episode);
    }

    public double getSimilarity() {
        if (similarity == null || similarity.equals("")) {
            return 0;
        }

        return Double.parseDouble(similarity);
    }

    public int getAniListId() {
        if (aniListId == null || aniListId.equals("")) {
            return 0;
        }
        return Integer.parseInt(aniListId);
    }
}