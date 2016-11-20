package com.rz.youtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

public class ActVideoPlayer extends AppCompatActivity {
    private Activity activity;
    private Context context;
    private YouTubePlayerSupportFragment youTubePlayerSupportFragment;
    private YouTubePlayer youTubePlayer = null;
    private Handler handler = new Handler();
    //
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    // YouTube player view
    //private YouTubePlayerFragment youTubeView;
    private YouTubePlayerSupportFragment youTubeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.act_video_player);

        activity = this;
        context = this;

        //youTubeView = (YouTubePlayerFragment) findViewById(R.id.youtube_fragment);
        youTubeView = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
        // Initializing video player with developer key
        youTubeView.initialize(Config.DEVELOPER_KEY, new OnYouVideoInitializedListener());

        //youTubeView.getDuration();
        handler.post(thread);
    }

    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            if (youTubePlayer != null) {
                System.out.println("CURRENT_TIME: " + youTubePlayer.getCurrentTimeMillis() / 1000 + ", DURATION: " + youTubePlayer.getDurationMillis() / 1000);
            }
            handler.postDelayed(this, 1000 * 20);
        }
    });

    class OnYouVideoInitializedListener implements YouTubePlayer.OnInitializedListener {
        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer argYouTubePlayer, boolean argWasRestored) {
            if (!argWasRestored) {
                // loadVideo() will auto play video
                // Use cueVideo() method, if you don't want to play it automatically
                //argYouTubePlayer.loadVideo(Config.YOUTUBE_VIDEO_CODE);
                // Hiding player controls
                //argYouTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                argYouTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                argYouTubePlayer.setPlayerStateChangeListener(new YouTubePlayerPlayerStateChangeListener());
                argYouTubePlayer.setPlaybackEventListener(playbackEventListener);
                argYouTubePlayer.loadVideo(Config.YOUTUBE_VIDEO_CODE);
                //argYouTubePlayer.loadVideo(Config.YOUTUBE_VIDEO_CODE, 1000 * 60);
                argYouTubePlayer.play();
                //argYouTubePlayer.getDuration();
                argYouTubePlayer.getCurrentTimeMillis();
                argYouTubePlayer.getDurationMillis();
                //argYouTubePlayer.seekToMillis(1000 * 60);
                System.out.println("CURRENT_TIME: " + argYouTubePlayer.getCurrentTimeMillis() + ", DURATION: " + argYouTubePlayer.getDurationMillis());
                youTubePlayer = argYouTubePlayer;
            }
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult argErrorReason) {
            if (argErrorReason.isUserRecoverableError()) {
                argErrorReason.getErrorDialog(activity, RECOVERY_DIALOG_REQUEST).show();
            } else {
                String errorMessage = String.format(getString(R.string.error_player), argErrorReason.toString());
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.DEVELOPER_KEY, new OnYouVideoInitializedListener());
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_fragment);
    }

    //|------------------------------------------------------------|
    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {

        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
        }

        @Override
        public void onPlaying() {
        }

        @Override
        public void onSeekTo(int arg0) {
        }

        @Override
        public void onStopped() {
        }

    };
    //|------------------------------------------------------------|

    class YouTubePlayerPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {

        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
            // ClsAppTools.logWrite("msg_youtube_video", "onVideoEnded");
        }

        @Override
        public void onVideoStarted() {
        }
    }

    ;
    //|------------------------------------------------------------|
}