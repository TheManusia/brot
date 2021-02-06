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
public class ResultsItem {

    @SerializedName("data")
    private Data data;

    @SerializedName("header")
    private ResultHeader resultHeader;
}