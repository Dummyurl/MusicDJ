package edm.music.t3v.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edm.music.t3v.R;
import edm.music.t3v.controller.MusicPlayerController;
import edm.music.t3v.listener.MusicPlayView;
import edm.music.t3v.model.Mp3Link;
import edm.music.t3v.network.service.SongRequest;
import edm.music.t3v.util.Constant;

public class PlayMusicActivity extends AppCompatActivity implements MusicPlayView, ExoPlayer.EventListener {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvCat)
    TextView tvCat;
    private SimpleExoPlayer player;
    //private SimpleExoPlayerView playerView;
    private String link;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady;
    private LikeButton likeButton;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuider;
    private NotificationManager mNotificationManager;
    private ImageView shareButton;

    private String musicName;
    private String authorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);
        ButterKnife.bind(this);
        likeButton = (LikeButton) findViewById(R.id.heart_button);
        shareButton = (ImageView) findViewById(R.id.btnShare);

        //playerView = (SimpleExoPlayerView) findViewById(R.id.playerView);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            link = bundle.getString(Constant.BUNDLE_URL);

            musicName = bundle.getString(Constant.BUNDLE_SONG_NAME, "");
            authorName = bundle.getString(Constant.BUNDLE_AUTHOR_NAME, "");

            tvTitle.setText(musicName);
            tvCat.setText(authorName);
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    String url = link;
                    intent.putExtra(Intent.EXTRA_TEXT,url);
                    startActivity(Intent.createChooser(intent,"Share with"));
                }
            });
        }

        MusicPlayerController musicPlayerController = new MusicPlayerController(this);
        musicPlayerController.getMusicToPlay(new SongRequest(link));

        initializeMediaSession();
        EventLike();
    }

    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(this, this.getClass().getSimpleName());

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuider = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuider.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }

    private void showNotification(PlaybackStateCompat state) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        int icon;
        String play_pause;
        if(state.getState() == PlaybackStateCompat.STATE_PLAYING){
            icon = R.drawable.exo_controls_pause;
            play_pause = getString(R.string.pause);
        } else {
            icon = R.drawable.exo_controls_play;
            play_pause = getString(R.string.play);
        }


        NotificationCompat.Action playPauseAction = new NotificationCompat.Action(
                icon, play_pause,
                MediaButtonReceiver.buildMediaButtonPendingIntent(this,
                        PlaybackStateCompat.ACTION_PLAY_PAUSE));

        NotificationCompat.Action restartAction = new android.support.v4.app.NotificationCompat
                .Action(R.drawable.exo_controls_previous, getString(R.string.restart),
                MediaButtonReceiver.buildMediaButtonPendingIntent
                        (this, PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS));

        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (this, 0, new Intent(this, PlayMusicActivity.class), 0);

        builder.setContentTitle(musicName)
                .setContentText(authorName)
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(restartAction)
                .addAction(playPauseAction)
                .setStyle(new NotificationCompat.MediaStyle()
                        .setMediaSession(mMediaSession.getSessionToken())
                        .setShowActionsInCompactView(0,1));


        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, builder.build());
    }

    private void initializePlayer(String link) {
        if (player == null) {

            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            player.addListener(this);

            //playerView.setPlayer(player);

            player.setPlayWhenReady(true);
            player.seekTo(currentWindow, playbackPosition);

            Uri uri = Uri.parse(link);
            MediaSource mediaSource = buildMediaSource(uri);

            player.prepare(mediaSource, true, false);

        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        // these are reused for both media sources we create below
        DefaultExtractorsFactory extractorsFactory =
                new DefaultExtractorsFactory();
        DefaultHttpDataSourceFactory dataSourceFactory =
                new DefaultHttpDataSourceFactory("user-agent");

        ExtractorMediaSource videoSource =
                new ExtractorMediaSource(uri, dataSourceFactory,
                        extractorsFactory, null, null);

        ExtractorMediaSource audioSource =
                new ExtractorMediaSource(uri, dataSourceFactory,
                        extractorsFactory, null, null);

        return new ConcatenatingMediaSource(audioSource, videoSource);
    }

    @Override
    public void onStart() {
        super.onStart();
//        if (Util.SDK_INT > 23) {
//            initializePlayer();
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        hideSystemUi();
//        if ((Util.SDK_INT <= 23 || player == null)) {
//            initializePlayer();
//        }

    }

//    @SuppressLint("InlinedApi")
//    private void hideSystemUi() {
//        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//    }

    @Override
    public void onPause() {
        super.onPause();
//        if (Util.SDK_INT <= 23) {
//            releasePlayer();
//        }
    }

    @Override
    public void onStop() {
        super.onStop();
//        if (Util.SDK_INT > 23) {
//            releasePlayer();
//        }
    }

    private void releasePlayer() {
        if (player != null) {
            mNotificationManager.cancelAll();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        releasePlayer();
//        mMediaSession.setActive(false);

    }

    @Override
    public void onGetMusicSuccess(List<Mp3Link> links) {
        initializePlayer(links.get(0).getSrc());
    }

    @Override
    public void onGetMusicFailure() {

    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuider.setState(PlaybackStateCompat.STATE_PLAYING,
                    player.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuider.setState(PlaybackStateCompat.STATE_PAUSED,
                    player.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuider.build());
        showNotification(mStateBuider.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            super.onPlay();

            player.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            super.onPause();

            player.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();

            player.seekTo(0);
        }
    }

    /**
     * Broadcast Receiver registered to receive the MEDIA_BUTTON intent coming from clients.
     */
    public static class MediaReceiver extends BroadcastReceiver {

        public MediaReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mMediaSession, intent);
        }
    }

    private void EventLike(){
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Toast.makeText(PlayMusicActivity.this, "You have add to favorite <3", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Toast.makeText(PlayMusicActivity.this, "You have unfavorite!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}