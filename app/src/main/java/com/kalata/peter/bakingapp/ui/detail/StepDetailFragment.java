package com.kalata.peter.bakingapp.ui.detail;

import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.kalata.peter.bakingapp.R;
import com.kalata.peter.bakingapp.common.utils.NetworkUtils;
import com.kalata.peter.bakingapp.common.utils.ParsingUtils;
import com.kalata.peter.bakingapp.data.local.entity.RecipeEntity;
import com.kalata.peter.bakingapp.data.local.entity.StepEntity;
import com.kalata.peter.bakingapp.ui.dialogs.ErrorDialogFragment;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepDetailFragment extends Fragment {

    private static final String ARGS_STEP = "args_step";
    private static final String EXO_STOP_POSITION = "stop_position";
    private static final String TAG = StepDetailFragment.class.getSimpleName();

    @Nullable
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.playerView)
    SimpleExoPlayerView playerView;

    private StepEntity step;
    private SimpleExoPlayer exoPlayer;
    private static MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private boolean isVideoAvailable = false;
    private long playerStopPosition = 0;

    public StepDetailFragment() {}

    public static StepDetailFragment newInstance(StepEntity step) {
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARGS_STEP, Parcels.wrap(step));
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            step = getStep();
            isVideoAvailable = !TextUtils.isEmpty(step.getVideoURL());
        }
    }

    private StepEntity getStep() {
        return Parcels.unwrap(getArguments().getParcelable(ARGS_STEP));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, view);
        if (savedInstanceState != null) playerStopPosition = savedInstanceState.getLong(EXO_STOP_POSITION);
        if (isVideoAvailable) {
            if (NetworkUtils.isConnected()) {
                initializeMediaSession();
                initializePlayer(Uri.parse(step.getVideoURL()));
            } else {
                if (savedInstanceState == null) {
                    ErrorDialogFragment.newInstance(getResources().getString(R.string.error_internet))
                            .show(getChildFragmentManager(), null);
                }
            }

        } else {
            playerView.setVisibility(View.GONE);
        }

        if (!getActivity().getResources().getBoolean(R.bool.is_tablet)) {
            if (isVideoAvailable) {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
            } else {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            }
        }

        if (tvTitle != null) tvTitle.setText(step.getDescription());
        return view;
    }

    private void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            playerView.setPlayer(exoPlayer);

            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.seekTo(playerStopPosition);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    private void initializeMediaSession() {

        if (mediaSession == null) {
            mediaSession = new MediaSessionCompat(getActivity(), TAG);
            mediaSession.setFlags(
                    MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                            MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

            mediaSession.setMediaButtonReceiver(null);

            mStateBuilder = new PlaybackStateCompat.Builder().setActions(
                    PlaybackStateCompat.ACTION_PLAY |
                            PlaybackStateCompat.ACTION_PAUSE |
                            PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                            PlaybackStateCompat.ACTION_PLAY_PAUSE);

            mediaSession.setPlaybackState(mStateBuilder.build());
            mediaSession.setCallback(new MySessionCallback());
        }

        mediaSession.setActive(true);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (isVideoAvailable) {
            initializePlayer(Uri.parse(step.getVideoURL()));
            initializeMediaSession();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (exoPlayer != null) {
            playerStopPosition = exoPlayer.getCurrentPosition();
            releasePlayer();
        }
        releaseMediaSession();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        releaseMediaSession();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isVideoAvailable) outState.putLong(EXO_STOP_POSITION, exoPlayer.getCurrentPosition());
    }

    private void releasePlayer() {
        if(exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    private void releaseMediaSession() {
        if (mediaSession != null) {
            mediaSession.setActive(false);
        }
    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            exoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            exoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            exoPlayer.seekTo(0);
        }
    }
}
