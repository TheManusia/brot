package xyz.themanusia.brot.network.saucenao.response;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultHeader {

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("dupes")
    private int dupes;

    @SerializedName("similarity")
    private String similarity;

    @SerializedName("index_id")
    private int indexId;

    @SerializedName("index_name")
    private String indexName;
}