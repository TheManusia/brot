package xyz.themanusia.brot.network.jikan.response;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Genre {

    @SerializedName("name")
    private String name;

    @SerializedName("mal_id")
    private int malId;

    @SerializedName("type")
    private String type;

    @SerializedName("url")
    private String url;
}