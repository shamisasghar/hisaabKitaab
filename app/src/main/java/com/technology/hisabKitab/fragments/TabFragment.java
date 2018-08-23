package com.technology.hisabKitab.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.technology.hisabKitab.R;
import com.technology.hisabKitab.toolbox.ToolbarListener;

public class TabFragment extends Fragment implements View.OnClickListener, ToolbarListener {
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private ViewPager mViewPager;
    TextView mNumberOfCartItemsText;
    private SectionsPagerAdapter sectionsPagerAdapter;
    Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ToolbarListener) {
            ((ToolbarListener) context).setTitle("Person Info");
        }
        mContext=context;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        return view;
    }


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//
//        inflater.inflate(R.menu.menu_main, menu);
//        View view = menu.findItem(R.id.notification_bell).getActionView();
//        mNumberOfCartItemsText = (TextView) view.findViewById(R.id.text_number_of_cart_items);
//    }



    @Override
    public void onClick(View v) {

    }

    @Override
    public void setTitle(String title) {
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    AddPersonFragment tab1 = new AddPersonFragment();
                    return tab1;
                case 1:
                    Update_DeleteFragment tab2 = new Update_DeleteFragment();
                    return tab2;
                case 2:
                    AddPersonFragment tab3 = new AddPersonFragment();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    String var = "Add person";

                    return var;
                case 1:
                    return "update/delete";
                case 2:
                    return "FAILED";
            }
            return null;
        }

    }

    @Override
    public void onResume() {
        if (mContext instanceof ToolbarListener) {
            ((ToolbarListener) mContext).setTitle("Person Info");
        }

        super.onResume();
    }
}
