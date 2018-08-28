package edm.music.t3v.network.service;

import edm.music.t3v.network.response.KindMusicResponse;
import edm.music.t3v.network.response.Mp3LinkResponse;
import edm.music.t3v.network.response.SongResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Vuong_IT on 19/11/2017.
 */

public interface ApiService {

    /**
     * @return list kind music
     */
    @GET("/")
    Call<KindMusicResponse> getKindMusic();

    /**
     * @param request object SongRequest contain url of kind music
     * @return list songs by kind music
     */
    @POST("classes/list")
    Call<SongResponse> getListSongs(@Body SongRequest request);


    /**
     *
     * @param request
     * @return
     */
    @POST("classes/getmusic")
    Call<Mp3LinkResponse> getMusicPlay(@Body SongRequest request);


}
