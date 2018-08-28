package edm.music.t3v.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import edm.music.t3v.model.Kind;

/**
 * Created by Vuong_IT on 19/11/2017.
 */

public class KindMusicResponse {
    @SerializedName("data")
    private List<Kind> kindMusic;

    public KindMusicResponse(List<Kind> kindMusic) {
        this.kindMusic = kindMusic;
    }

    public List<Kind> getKindMusic() {
        return kindMusic;
    }

    public void setKindMusic(List<Kind> kindMusic) {
        this.kindMusic = kindMusic;
    }
}
