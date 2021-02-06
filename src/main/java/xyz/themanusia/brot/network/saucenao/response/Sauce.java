package xyz.themanusia.brot.network.saucenao.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sauce {
    private String similarity;
    private String thumbnail;
    private String name;
    private String indexName;
    private String url;
}
