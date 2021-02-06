package xyz.themanusia.brot.network.saucenao.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SauceNaoResponse {

    @SerializedName("results")
    private List<ResultsItem> results;

    @SerializedName("header")
    private Header header;

}