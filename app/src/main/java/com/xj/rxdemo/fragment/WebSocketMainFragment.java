package com.xj.rxdemo.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xj.rxdemo.R;


/**
 * Created by xj
 * on 2016/2/25.
 */
public class WebSocketMainFragment extends Fragment {

    private View mContentView;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private int currentPagerIndex = -1;
    private WebSocketFragment webSocketFragment1;
    private WebSocketFragment webSocketFragment2;
    public static final int INDEX_OF_MAIL = 0;
    public static final int INDEX_OF_CONTACTS = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.web_socket_main_fragment, container, false);
        initData();
        findViews();
        setAdapters();
        return mContentView;
    }

    private void initData() {
        currentPagerIndex = INDEX_OF_MAIL;
    }

    private void findViews() {
        viewPager = (ViewPager) mContentView.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) mContentView.findViewById(R.id.tabs);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentPagerIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    private void setAdapters() {
        viewPager.setAdapter(new MailMainsAdapter(this));
        tabLayout.setupWithViewPager(viewPager);
    }

    private class MailMainsAdapter extends FragmentPagerAdapter {

        private Resources resources;

        public MailMainsAdapter(Fragment fragment) {
            super(fragment.getChildFragmentManager());
            this.resources = fragment.getResources();
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case INDEX_OF_CONTACTS: {
                    if (webSocketFragment2 == null) {
                        webSocketFragment2 = new WebSocketFragment();
                    }
                    return webSocketFragment2;
                }
                default: {
                    if (webSocketFragment1 == null) {
                        webSocketFragment1 = new WebSocketFragment();
                    }
                    return webSocketFragment1;
                }
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case INDEX_OF_CONTACTS: {
                    return "右边";
                }
                default: {
                    return "左边";
                }
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webSocketFragment2 != null) {
            webSocketFragment2.recycle();
            webSocketFragment2 = null;
        }

        if (webSocketFragment1 != null) {
            webSocketFragment1.recycle();
            webSocketFragment1 = null;
        }
    }

}
