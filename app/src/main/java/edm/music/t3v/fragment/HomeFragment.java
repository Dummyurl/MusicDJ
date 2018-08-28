package edm.music.t3v.fragment;

/**
 * Created by hoangmobi on 5/19/2016.
 */

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edm.music.t3v.R;
import edm.music.t3v.controller.KindMusicController;
import edm.music.t3v.custom.MusicPagerAdapter;
import edm.music.t3v.custom.MusicViewPager;
import edm.music.t3v.listener.KindMusicView;
import edm.music.t3v.model.Kind;


@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment implements KindMusicView {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.vpMusic)
    MusicViewPager vpMusic;
    Unbinder unbinder;

    private MusicPagerAdapter pagerAdapter;
    private List<Kind> kindList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        initViewPager();
        return rootView;
    }


    private void initViewPager() {

        pagerAdapter = new MusicPagerAdapter(kindList, getFragmentManager());
        vpMusic.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(vpMusic);
        vpMusic.setEnableSwipe(true);
        KindMusicController controller = new KindMusicController(this);
        controller.getKindMusic();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onGetKindMusicSuccess(List<Kind> kindList) {
        this.kindList.clear();
        this.kindList.addAll(kindList);
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetKindMusicFailure() {

    }
}