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

public class Manga {

    @SerializedName("mal_id")
    private int malId;

    @SerializedName("title")
    private String title;

    @SerializedName("title_english")
    private String titleEn;

    @SerializedName("status")
    private String status;

    @SerializedName("volumes")
    private int volumes;

    @SerializedName("chapters")
    private int chapters;

    @SerializedName("synopsis")
    private String synopsis;

    @SerializedName("url")
    private String url;

    @SerializedName("image_url")
    private String img;

    @SerializedName("type")
    private String type;

    @SerializedName("score")
    private double score;

    @SerializedName("genres")
    private ArrayList<Genre> genres;
}
