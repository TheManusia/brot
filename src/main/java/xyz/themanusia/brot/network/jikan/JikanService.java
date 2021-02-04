package xyz.themanusia.brot.network.jikan;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import xyz.themanusia.brot.network.jikan.response.Anime;
import xyz.themanusia.brot.network.jikan.response.AnimeSearch;
import xyz.themanusia.brot.network.jikan.response.Manga;
import xyz.themanusia.brot.network.jikan.response.MangaSearch;

public interface JikanService {
    @GET("anime/{id}")
    Call<Anime> getAnimeById(@Path("id") int id);

    @GET("search/anime")
    Call<AnimeSearch> getAnimes(@Query("q") String keyword);

    @GET("manga/{id}")
    Call<Manga> getMangaById(@Path("id") int id);

    @GET("search/manga")
    Call<MangaSearch> getMangas(@Query("q") String keyword);
}
