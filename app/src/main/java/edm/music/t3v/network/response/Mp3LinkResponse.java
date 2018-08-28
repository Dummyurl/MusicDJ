package edm.music.t3v.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import edm.music.t3v.model.Mp3Link;

/**
 * Created by Vuong_IT on 21/11/2017.
 */

public class Mp3LinkResponse {

    @SerializedName("data")
    private List<Mp3Link> mp3Links;

    public Mp3LinkResponse(List<Mp3Link> mp3Links) {
        this.mp3Links = mp3Links;
    }

    public List<Mp3Link> getMp3Links() {
        return mp3Links;
    }

    public void setMp3Links(List<Mp3Link> mp3Links) {
        this.mp3Links = mp3Links;
    }
}
