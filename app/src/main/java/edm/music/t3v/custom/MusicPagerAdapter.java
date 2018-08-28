package edm.music.t3v.custom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import edm.music.t3v.fragment.MusicFragment;
import edm.music.t3v.model.Kind;
import edm.music.t3v.util.Constant;

/**
 * Created by Vuong_IT on 19/11/2017.
 */

public class MusicPagerAdapter extends FragmentStatePagerAdapter {
    private List<Kind> kindList;

    public MusicPagerAdapter(List<Kind> kindList, FragmentManager fm) {
        super(fm);
        this.kindList = kindList;
    }

    @Override
    public Fragment getItem(int position) {
        MusicFragment fragment = new MusicFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.BUNDLE_URL, kindList.get(position).getLink());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return kindList.get(position).getName();
    }

    @Override
    public int getCount() {
        return kindList.size();
    }
}
