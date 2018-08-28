package edm.music.t3v.listener;

import java.util.List;

import edm.music.t3v.model.Mp3Link;

/**
 * Created by Vuong_IT on 21/11/2017.
 */

public interface MusicPlayView {
    void onGetMusicSuccess(List<Mp3Link> links);

    void onGetMusicFailure();
}
