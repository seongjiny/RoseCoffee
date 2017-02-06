package edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rose_hulman.zhiqiangqiu.rosecoffee.MainActivity;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.R;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDeliveryFragment extends Fragment {
    User mUser;

    public MyDeliveryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mUser = ((MainActivity)getActivity()).getUser();
        View view;
        Log.d("DD",mUser.isCustomer()+"");
        Log.d("DD",mUser+"");
        if(mUser.isCustomer()){
            view =inflater.inflate(R.layout.fragment_customer_main,container,false);
        }else{
            view = inflater.inflate(R.layout.fragment_delivery_main,container,false);
        }
        return view;
    }


}
