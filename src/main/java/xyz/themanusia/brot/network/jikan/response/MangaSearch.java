package xyz.themanusia.brot.network.jikan.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MangaSearch{

	@SerializedName("results")
	private List<MangaSearchItem> results;
}