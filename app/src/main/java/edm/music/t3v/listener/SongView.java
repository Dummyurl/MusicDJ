package edm.music.t3v.listener;

import java.util.List;

import edm.music.t3v.model.Song;

/**
 * Created by Vuong_IT on 20/11/2017.
 */

public interface SongView {

    void onGetSongsSuccess(List<Song> songs);

    void onGetSongsFailure();

}
