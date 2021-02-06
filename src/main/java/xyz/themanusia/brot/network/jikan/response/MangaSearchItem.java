package xyz.themanusia.brot.network.jikan.response;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MangaSearchItem {

    @SerializedName("mal_id")
    private int malId;

    @SerializedName("title")
    private String title;
}