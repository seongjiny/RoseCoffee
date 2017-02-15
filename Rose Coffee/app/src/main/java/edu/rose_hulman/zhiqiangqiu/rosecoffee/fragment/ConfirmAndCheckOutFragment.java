package edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import edu.rose_hulman.zhiqiangqiu.rosecoffee.MainActivity;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.MenuItem;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.Order;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.R;

public class ConfirmAndCheckOutFragment extends Fragment {
    View mConfirmLayout=null;
    Order mOrder;
    public ConfirmAndCheckOutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_confirm_and_check_out, container, false);
        final TextView confirmButton = (TextView) view.findViewById(R.id.confrim_and_pay_text);
        mConfirmLayout = view.findViewById(R.id.confirm_detail_layout);
        mOrder = ((MainActivity)getActivity()).getOrder();
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBar pb = (ProgressBar)view.findViewById(R.id.confirm_progress);
                pb.setVisibility(View.VISIBLE);
                confirmButton.setText("Searching...");
                confirmButton.setClickable(false);
            }
        });
        TextView locationView = (TextView)mConfirmLayout.findViewById(R.id.cus_confirm_location);
        TextView timeView = (TextView) mConfirmLayout.findViewById(R.id.cus_confirm_time);

        locationView.setText(mOrder.getLocation());
        timeView.setText(mOrder.getTime());
        return view;
    }
    public void editOrderItemInformation(ArrayList<MenuItem> allMenu){
        int size = allMenu.size();
        String name = allMenu.get(0).getName();
        TextView view = (TextView)mConfirmLayout.findViewById(R.id.confirm_order_detail);
        if(size<=1){
            view.setText(name);
        }else if(size==2){
            view.setText(name+" and "+(size-1)+" other");
        }else{
            view.setText(name+" and "+(size-1)+" others");
        }
    }

}
