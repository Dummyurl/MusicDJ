package edm.music.t3v.controller;

import edm.music.t3v.listener.KindMusicView;
import edm.music.t3v.network.response.KindMusicResponse;
import edm.music.t3v.network.service.ApiService;
import edm.music.t3v.network.service.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vuong_IT on 19/11/2017.
 */

public class KindMusicController {

    private KindMusicView view;

    public KindMusicController(KindMusicView view) {
        this.view = view;
    }

    public void getKindMusic() {
        ApiService api = RetrofitClient.getApiService();
        Call<KindMusicResponse> call = api.getKindMusic();
        call.enqueue(new Callback<KindMusicResponse>() {
            @Override
            public void onResponse(Call<KindMusicResponse> call, Response<KindMusicResponse> response) {
                if (response.isSuccessful()) {
                    view.onGetKindMusicSuccess(response.body().getKindMusic());
                } else {
                    view.onGetKindMusicFailure();
                }
            }

            @Override
            public void onFailure(Call<KindMusicResponse> call, Throwable t) {
                view.onGetKindMusicFailure();
            }
        });
    }
}
