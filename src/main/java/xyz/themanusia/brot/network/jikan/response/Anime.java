package xyz.themanusia.brot.network.jikan.response;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Anime {

    @SerializedName("score")
    private double score;

    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("title_english")
    private String titleEnglish;

    @SerializedName("mal_id")
    private int malId;

    @SerializedName("synopsis")
    private String synopsis;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String url;

    @SerializedName("episodes")
    private int episodes;

    @SerializedName("status")
    private String status;

    @SerializedName("rating")
    private String rating;

    @SerializedName("genres")
    private ArrayList<Genre> genres;

}
