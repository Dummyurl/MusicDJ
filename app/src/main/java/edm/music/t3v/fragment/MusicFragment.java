package edm.music.t3v.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edm.music.t3v.R;
import edm.music.t3v.activity.PlayMusicActivity;
import edm.music.t3v.adapter.SongAdapter;
import edm.music.t3v.base.BaseFragment;
import edm.music.t3v.controller.SongController;
import edm.music.t3v.listener.SongView;
import edm.music.t3v.model.Song;
import edm.music.t3v.network.service.SongRequest;
import edm.music.t3v.util.Constant;

/**
 * Created by Vuong_IT on 19/11/2017.
 */

public class MusicFragment extends BaseFragment implements SongAdapter.OnItemClick, SongView {

    Unbinder unbinder;
    @BindView(R.id.rvSongs)
    RecyclerView rvSongs;
    @BindView(R.id.rowLoadmore)
    LinearLayout rowLoadmore;
    @BindView(R.id.vLoading)
    AVLoadingIndicatorView vLoading;

    private int page = 1;

    private String link;
    private List<Song> songs = new ArrayList<>();
    private SongAdapter songAdapter;
    private boolean isLoadmore = true;

    @Override
    protected void initData() {

        vLoading.show();

        getSongs();
    }

    private void getSongs() {
        SongController songController = new SongController(this);
        SongRequest request = new SongRequest(link);
        request.setPage(page);
        songController.getSongs(request);
    }

    @Override
    protected void initView(View v) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            link = bundle.getString(Constant.BUNDLE_URL, "");
        }

        final LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        rvSongs.setLayoutManager(layoutManager);

        songAdapter = new SongAdapter(getContext(), songs, this);
        rvSongs.setAdapter(songAdapter);


        rvSongs.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
//                    if (!Utils.isConnectInternet(mActivity)) {
//                        ToastUtils.showToastError(mActivity, R.string.no_internet_connection);
//                        return;
//                    }
//
//                    if (!canLoadmore) return;
//                    if (isLoadmore) return;
//                    if (isRefreshing) return;

                    if (isLoadmore) return;

                    int totalCount = layoutManager.getItemCount();
                    int childCount = layoutManager.getChildCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    if (childCount + firstVisibleItemPosition >= totalCount - 2) {
                        isLoadmore = true;
                        rowLoadmore.setVisibility(View.VISIBLE);
                        getSongs();
                    }
                }

            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_music;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClickItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.BUNDLE_URL, songs.get(position).getLink());
        bundle.putString(Constant.BUNDLE_SONG_NAME, songs.get(position).getName());
        bundle.putString(Constant.BUNDLE_AUTHOR_NAME, songs.get(position).getPoster());

        Intent intent = new Intent(getContext(), PlayMusicActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    public void onGetSongsSuccess(List<Song> songs) {
        if (page == 1) {

            isLoadmore = false;

            this.songs.clear();
            this.songs.addAll(songs);

            songAdapter.notifyDataSetChanged();

            page = page + 1;

            if (isAdded()) {
                vLoading.hide();
                rowLoadmore.setVisibility(View.GONE);        
            }

        } else {

            isLoadmore = false;

            this.songs.addAll(songs);

            songAdapter.notifyDataSetChanged();

            page = page + 1;

            rowLoadmore.setVisibility(View.GONE);

        }
    }

    @Override
    public void onGetSongsFailure() {

    }
}
