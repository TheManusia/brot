package xyz.themanusia.brot.network.jikan.response;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnimeSearch{

	@SerializedName("results")
	private ArrayList<AnimeSearchItem> results;
}