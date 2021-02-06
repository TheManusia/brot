package xyz.themanusia.brot.network.tracemoe.response;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor

public class Sauce {

    @SerializedName("episode")
    private String episode;

    @SerializedName("mal_id")
    private String malId;

    @SerializedName("similarity")
    private String similarity;

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
}