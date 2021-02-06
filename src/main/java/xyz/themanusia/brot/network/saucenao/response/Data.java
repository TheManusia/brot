package xyz.themanusia.brot.network.saucenao.response;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Data {

    @SerializedName("est_time")
    private String estTime;

    @SerializedName("ext_urls")
    private List<String> extUrls;

    @SerializedName("anidb_aid")
    private int anidbAid;

    @SerializedName("year")
    private String year;

    @SerializedName("part")
    private String part;

    @SerializedName("source")
    private String source;
}