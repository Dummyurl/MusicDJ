package edm.music.t3v.controller;

import edm.music.t3v.listener.MusicPlayView;
import edm.music.t3v.network.response.Mp3LinkResponse;
import edm.music.t3v.network.service.ApiService;
import edm.music.t3v.network.service.RetrofitClient;
import edm.music.t3v.network.service.SongRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vuong_IT on 21/11/2017.
 */

public class MusicPlayerController {

    private MusicPlayView view;

    public MusicPlayerController(MusicPlayView view) {
        this.view = view;
    }

    public void getMusicToPlay(SongRequest request) {
        ApiService api = RetrofitClient.getApiService();
        Call<Mp3LinkResponse> call = api.getMusicPlay(request);
        call.enqueue(new Callback<Mp3LinkResponse>() {
            @Override
            public void onResponse(Call<Mp3LinkResponse> call, Response<Mp3LinkResponse> response) {
                if (response.isSuccessful()) {
                    view.onGetMusicSuccess(response.body().getMp3Links());
                } else {
                    view.onGetMusicFailure();
                }
            }

            @Override
            public void onFailure(Call<Mp3LinkResponse> call, Throwable t) {
                view.onGetMusicFailure();
            }
        });
    }
}
