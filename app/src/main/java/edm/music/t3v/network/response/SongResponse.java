package edm.music.t3v.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import edm.music.t3v.model.Song;

/**
 * Created by Vuong_IT on 20/11/2017.
 */

public class SongResponse {

    @SerializedName("data")
    private List<Song> songs;

    public SongResponse(List<Song> songs) {
        this.songs = songs;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
