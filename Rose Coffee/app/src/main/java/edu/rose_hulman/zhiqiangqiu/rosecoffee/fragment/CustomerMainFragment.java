package edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rose_hulman.zhiqiangqiu.rosecoffee.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerMainFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3 ;
    private MyAdapter mAdapter;

    public CustomerMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View rootView =  inflater.inflate(R.layout.tabbed_customer_main,null);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

        /**
         *Set an Apater for the View Pager
         */
        mAdapter = new MyAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return rootView;

    }

    private class MyAdapter extends FragmentPagerAdapter {
        LocationAndTimeFragment mLocationFragment;
        OrderDetailFragment mOrderDetailFragment;
        public ConfirmAndCheckOutFragment mConfirmFragment;
        public MyAdapter(FragmentManager fm) {
            super(fm);
            mLocationFragment= new LocationAndTimeFragment();
            mOrderDetailFragment = new OrderDetailFragment();
            mConfirmFragment = new ConfirmAndCheckOutFragment();
            mOrderDetailFragment.sendFragment(mConfirmFragment);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            switch (position) {
                case 0 : return mLocationFragment;
                case 1 : return mOrderDetailFragment;
                case 2 : return mConfirmFragment;
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Location and Time";
                case 1 :
                    return "Order Detail";
                case 2 :
                    return "Confirm and Check Out";
            }
            return null;
        }

    }

}
