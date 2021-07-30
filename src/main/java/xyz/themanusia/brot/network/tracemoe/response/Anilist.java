package xyz.themanusia.brot.network.tracemoe.response;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Anilist {

    @SerializedName("id")
    private int id;

    @SerializedName("idMal")
    private int idMal;

    @SerializedName("title")
    private Title title;

    @SerializedName("isAdult")
    private boolean isAdult;
}