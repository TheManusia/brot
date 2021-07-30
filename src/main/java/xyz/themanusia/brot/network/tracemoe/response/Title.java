package xyz.themanusia.brot.network.tracemoe.response;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Title{

	@SerializedName("native")
	private String nativeTitle;

	@SerializedName("romaji")
	private String romajiTitle;

	@SerializedName("english")
	private Object englishTitle;
}