package xyz.themanusia.brot.network.saucenao;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xyz.themanusia.brot.network.saucenao.response.SauceNaoResponse;

public interface SauceNaoService {
    @GET("search.php")
    Call<SauceNaoResponse> getSauce(@Query("api_key") String apiKey,
                                    @Query("db") String source,
                                    @Query("output_type") String output,
                                    @Query("numres") String outputNum,
                                    @Query("url") String image);
}
