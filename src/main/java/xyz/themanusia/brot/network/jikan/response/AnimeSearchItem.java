package xyz.themanusia.brot.network.jikan.response;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnimeSearchItem {

	@SerializedName("mal_id")
	private int malId;

	@SerializedName("title")
	private String title;

}