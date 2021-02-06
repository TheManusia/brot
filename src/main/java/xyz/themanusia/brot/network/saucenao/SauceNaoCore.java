package xyz.themanusia.brot.network.saucenao;

import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.themanusia.brot.Main;
import xyz.themanusia.brot.network.saucenao.callback.SauceCallback;
import xyz.themanusia.brot.network.saucenao.response.Data;
import xyz.themanusia.brot.network.saucenao.response.ResultHeader;
import xyz.themanusia.brot.network.saucenao.response.Sauce;
import xyz.themanusia.brot.network.saucenao.response.SauceNaoResponse;

public class SauceNaoCore {
    private final SauceNaoService sauceNaoService;

    public SauceNaoCore() {
        RetrofitSaucenaoService retrofitSaucenaoService = new RetrofitSaucenaoService();
        sauceNaoService = retrofitSaucenaoService.getService();
    }

    public void getSauce(String image, SauceCallback callback) {
        sauceNaoService.getSauce(Main.SAUCENAO_API,
                "999",
                "2",
                "1",
                image)
                .enqueue(new Callback<SauceNaoResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<SauceNaoResponse> call, @NotNull Response<SauceNaoResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                int status = response.body().getHeader().getStatus();
                                if (status == 0) {
                                    ResultHeader header = response.body().getResults().get(0).getResultHeader();
                                    Data data = response.body().getResults().get(0).getData();
                                    callback.onSuccess(new Sauce(
                                            header.getSimilarity(),
                                            header.getThumbnail(),
                                            data.getSource(),
                                            header.getIndexName(),
                                            (data.getExtUrls().isEmpty()) ? "" : data.getExtUrls().get(0)
                                    ));
                                } else if (status > 0) {
                                    callback.onFailure("An Error Occurred: 500");
                                } else {
                                    callback.onFailure("An Error Occurred: 400");
                                }
                            } else {
                                callback.onFailure("An Error Occurred: 404");
                            }
                        } else {
                            callback.onFailure("An Error Occurred: 400");
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<SauceNaoResponse> call, @NotNull Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                });
    }
}
