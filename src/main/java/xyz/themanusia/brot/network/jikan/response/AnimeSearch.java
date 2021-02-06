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

public class AnimeSearch {

    @SerializedName("results")
    private ArrayList<AnimeSearchItem> results;
}