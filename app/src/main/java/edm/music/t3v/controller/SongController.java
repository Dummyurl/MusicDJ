package edm.music.t3v.controller;

import edm.music.t3v.listener.SongView;
import edm.music.t3v.network.response.SongResponse;
import edm.music.t3v.network.service.ApiService;
import edm.music.t3v.network.service.RetrofitClient;
import edm.music.t3v.network.service.SongRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vuong_IT on 20/11/2017.
 */

public class SongController {
    private SongView view;

    public SongController(SongView view) {
        this.view = view;
    }


    public void getSongs(final SongRequest request) {
        ApiService api = RetrofitClient.getApiService();
        Call<SongResponse> call = api.getListSongs(request);
        call.enqueue(new Callback<SongResponse>() {
            @Override
            public void onResponse(Call<SongResponse> call, Response<SongResponse> response) {
                if (response.isSuccessful()) {
                    view.onGetSongsSuccess(response.body().getSongs());
                } else {
                    view.onGetSongsFailure();
                }
            }

            @Override
            public void onFailure(Call<SongResponse> call, Throwable t) {
                view.onGetSongsFailure();
            }
        });
    }

}
