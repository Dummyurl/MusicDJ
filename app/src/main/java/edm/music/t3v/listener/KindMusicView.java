package edm.music.t3v.listener;

import java.util.List;

import edm.music.t3v.model.Kind;

/**
 * Created by Vuong_IT on 19/11/2017.
 */

public interface KindMusicView {

    void onGetKindMusicSuccess(List<Kind> kindList);

    void onGetKindMusicFailure();
}
